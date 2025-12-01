package net.platform.services.ecosystem.adapter.configuration.kafka;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;

@Configuration
public class KafkaStreamsTopology {

    // 1. Định nghĩa Tên Topics
    private static final String INPUT_TOPIC = "ecosystem_server.ecosystem.users";
    private static final String OUTPUT_TOPIC = "ecosystem_server.kafka_streams.convert_users";

    // Tiêm các giá trị cấu hình Schema Registry từ application.yml
    @Value("${spring.kafka.streams.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.streams.properties.basic.auth.user.info}")
    private String basicAuthUserInfo;

    @Value("${spring.kafka.streams.properties.basic.auth.credentials.source}")
    private String basicAuthCredentialsSource;


    @Bean
    public KStream<String, String> processUserCdcStream(StreamsBuilder streamsBuilder) {
        final GenericAvroSerde genericAvroSerde = new GenericAvroSerde();
        final Map<String, String> serdeConfig = Map.of(
                "schema.registry.url", schemaRegistryUrl,
                "basic.auth.user.info", basicAuthUserInfo,
                "basic.auth.credentials.source", basicAuthCredentialsSource
        );

        // Cấu hình cho Key Serde
        genericAvroSerde.configure(serdeConfig, true);
        // Cấu hình cho Value Serde
        // CHÚ Ý: GenericAvroSerde này sẽ được sử dụng cho cả Key và Value CDC.
        // Cần khởi tạo 2 instance nếu Key và Value có Schema khác nhau,
        // nhưng với Debezium, Key thường là Avro và Value là Avro.

        // 2. Đọc luồng CDC thô
        // Key: GenericRecord (chứa Key của MySQL), Value: GenericRecord (dữ liệu CDC)
        KStream<GenericRecord, GenericRecord> cdcStream = streamsBuilder.stream(
                INPUT_TOPIC,
                // Sử dụng GenericAvroSerde cho cả Key và Value
                Consumed.with(genericAvroSerde, genericAvroSerde)
        );

        // 3. Lọc và Chuyển đổi (Transformation)
        KStream<String, String> convertedStream = cdcStream
                // Chỉ quan tâm đến sự kiện CREATE ('c') và UPDATE ('u')
                .filter((key, value) -> isCreateOrUpdate(value))

                // Chuyển đổi Key (từ GenericRecord sang String ID) và Value (từ GenericRecord sang String mới)
                .map((key, value) -> {
                    // Key mới: Lấy ID người dùng (chắc chắn tồn tại trong Key Record của Debezium)
                    String newKey = Objects.toString(key.get("id"));

                    // Value mới: Trích xuất và xử lý
                    GenericRecord afterRecord = (GenericRecord) value.get("after");
                    if (afterRecord == null) {
                        // Xử lý sự kiện DELETE (value.get("before") sẽ có dữ liệu)
                        // Nếu là DELETE, chúng ta có thể trả về một giá trị đặc biệt hoặc bỏ qua
                        return null;
                    }

                    // Lấy các trường cần thiết từ 'after'
                    String username = afterRecord.get("username").toString();
                    String firstName = afterRecord.get("first_name").toString();
                    String lastName = afterRecord.get("last_name").toString();

                    // Yêu cầu: fullname được uppercase
                    String fullName = (firstName + " " + lastName).toUpperCase();

                    // Tạo chuỗi Value đầu ra theo định dạng bạn muốn (ví dụ: JSON String đơn giản)
                    String newValue = String.format("{\"username\":\"%s\", \"fullname\":\"%s\"}", username, fullName);

                    return KeyValue.pair(newKey, newValue);
                })
                // Lọc các bản ghi null (ví dụ: do sự kiện DELETE hoặc map trả về null)
                .filter((key, value) -> key != null && value != null);

        // 4. Ghi ra Topic đích (dùng String Serde)
        convertedStream.to(
                OUTPUT_TOPIC,
                // Sử dụng Serdes.String() cho đầu ra (Key: String, Value: String)
                Produced.with(Serdes.String(), Serdes.String())
        );

        System.out.println("CDC Stream Conversion Topology configured: " + INPUT_TOPIC + " -> " + OUTPUT_TOPIC);

        return convertedStream;
    }

    private boolean isCreateOrUpdate(GenericRecord cdcValue) {
        if (cdcValue == null) return false;
        Object op = cdcValue.get("op");
        if (op == null) return false;
        // Kiểm tra op là 'c' (Create) hoặc 'u' (Update)
        String opStr = op.toString();
        return "c".equals(opStr) || "u".equals(opStr);
    }
}
