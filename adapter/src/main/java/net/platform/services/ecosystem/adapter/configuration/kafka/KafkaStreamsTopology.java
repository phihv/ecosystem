package net.platform.services.ecosystem.adapter.configuration.kafka;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaStreamsTopology {

    // 1. Định nghĩa Tên Topics
    private static final String INPUT_TOPIC = "users-in";
    private static final String OUTPUT_TOPIC = "users-out";

    /**
     * Phương thức này định nghĩa logic xử lý luồng (Topology).
     * Spring Boot sẽ tự động inject StreamsBuilder vào đây.
     */
    @Bean
    public KStream<String, String> processUserStream(StreamsBuilder streamsBuilder) {

        // 2. Định nghĩa nguồn (Source)
        // streamsBuilder.stream(Tên Topic) tạo ra một KStream
        KStream<String, String> userStream = streamsBuilder.stream(INPUT_TOPIC);

        // 3. Định nghĩa logic xử lý (Processor)
        // Ví dụ: Lọc ra các tin nhắn Key là null và chuyển đổi Value thành chữ IN HOA
        KStream<String, String> processedStream = userStream
                .filter((key, value) -> key != null) // Bỏ qua tin nhắn không có Key
                .mapValues(value -> {
                    System.out.println("Processing: " + value);
                    return value.toUpperCase(); // Chuyển sang in hoa
                });

        // 4. Định nghĩa đích (Sink)
        // Ghi kết quả đã xử lý vào topic đầu ra
        processedStream.to(OUTPUT_TOPIC);

        System.out.println("Kafka Streams Topology configured: " + INPUT_TOPIC + " -> " + OUTPUT_TOPIC);

        // Trả về stream (tùy chọn)
        return processedStream;
    }
}
