FROM confluentinc/cp-kafka-connect:latest

USER root

# 1. Cài đặt Avro Converter (Giữ nguyên, vì bước này đã thành công)
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-avro-converter:latest

# 2. Cài đặt Debezium MySQL Connector (SỬA PHIÊN BẢN TẠI ĐÂY)
RUN confluent-hub install --no-prompt debezium/debezium-connector-mysql:latest

USER appuser