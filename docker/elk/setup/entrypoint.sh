#!/bin/bash

if [ x${ELASTIC_PASSWORD} == x ]; then
  echo "Set the ELASTIC_PASSWORD environment variable in the .env file";
  exit 1;
elif [ x${KIBANA_PASSWORD} == x ]; then
  echo "Set the KIBANA_PASSWORD environment variable in the .env file";
  exit 1;
elif [ x${LOGSTASH_PASSWORD} == x ]; then
  echo "Set the LOGSTASH_PASSWORD environment variable in the .env file";
  exit 1;
fi;

# Đường dẫn chứng chỉ
CERTS_DIR="/usr/share/elasticsearch/config/certs"

if [ ! -f ${CERTS_DIR}/ca.zip ]; then
  echo "Creating CA";
  bin/elasticsearch-certutil ca --silent --pem -out ${CERTS_DIR}/ca.zip;
  if [ -d ${CERTS_DIR}/ca ]; then
        echo "Cleaning up existing 'ca' directory...";
        rm -rf ${CERTS_DIR}/ca;
    fi;
  # THÊM TÙY CHỌN -o VÀ SỬA ĐƯỜNG DẪN GIẢI NÉN
  unzip -o ${CERTS_DIR}/ca.zip -d ${CERTS_DIR};
fi;

if [ ! -f ${CERTS_DIR}/certs.zip ]; then
  echo "Creating certs";
  echo -ne \
  "instances:\n"\
  "  - name: es01\n"\
  "    dns:\n"\
  "      - es01\n"\
  "      - localhost\n"\
  "    ip:\n"\
  "      - 127.0.0.1\n"\
  "  - name: es02\n"\
  "    dns:\n"\
  "      - es02\n"\
  "      - localhost\n"\
  "    ip:\n"\
  "      - 127.0.0.1\n"\
  "  - name: es03\n"\
  "    dns:\n"\
  "      - es03\n"\
  "      - localhost\n"\
  "    ip:\n"\
  "      - 127.0.0.1\n"\
  "  - name: logstash\n"\
  "    dns:\n"\
  "      - logstash\n"\
  "      - localhost\n"\
  "    ip:\n"\
  "      - 127.0.0.1\n"\
  > ${CERTS_DIR}/instances.yml;

  bin/elasticsearch-certutil cert --silent --pem \
    -out ${CERTS_DIR}/certs.zip \
    --in ${CERTS_DIR}/instances.yml \
    --ca-cert ${CERTS_DIR}/ca/ca.crt \
    --ca-key ${CERTS_DIR}/ca/ca.key;

  unzip -o ${CERTS_DIR}/certs.zip -d ${CERTS_DIR};
fi;

echo "Setting file permissions";
# QUAN TRỌNG: Phải chown cho user 1000 (elasticsearch/logstash default user)
# Nếu để root:root, các container kia sẽ bị lỗi "Permission denied"
chown -R 1000:0 ${CERTS_DIR};
find ${CERTS_DIR} -type d -exec chmod 750 {} \;;
find ${CERTS_DIR} -type f -exec chmod 640 {} \;;

echo "Waiting for Elasticsearch availability";
until curl -s --cacert ${CERTS_DIR}/ca/ca.crt https://es01:9200 | grep -q "missing authentication credentials"; do sleep 30; done;

echo "Setting kibana_system password";
until curl -s -X POST --cacert ${CERTS_DIR}/ca/ca.crt \
  -u "elastic:${ELASTIC_PASSWORD}" \
  -H "Content-Type: application/json" \
  https://es01:9200/_security/user/kibana_system/_password \
  -d "{\"password\":\"${KIBANA_PASSWORD}\"}" | grep -q "^{}"; do sleep 10; done;


echo "Creating or updating logstash_writer role";
until curl -s -X PUT --cacert ${CERTS_DIR}/ca/ca.crt \
  -u "elastic:${ELASTIC_PASSWORD}" \
  -H "Content-Type: application/json" \
  https://es01:9200/_security/role/logstash_writer \
  -d '{
    "cluster": ["manage_index_templates","monitor"],
    "indices": [
      {
        "names": ["logs-*","filebeat-*"],
        "privileges": ["write","create","create_index","read"]
      }
    ]
  }' | grep -q '"role"'; do
  echo "Retrying creating logstash_writer role...";
  sleep 10;
done;

echo "Creating or updating logstash_internal user";
# Dùng grep '{"user":' hoặc 'created' để xác nhận thành công
until curl -X POST --cacert ${CERTS_DIR}/ca/ca.crt \
  -u "elastic:${ELASTIC_PASSWORD}" \
  -H "Content-Type: application/json" \
  https://es01:9200/_security/user/logstash_internal \
  -d "{\"password\":\"${LOGSTASH_PASSWORD}\", \"roles\": [\"logstash_writer\", \"beats_system\"]}" \
  | grep -q '"created"'; do
  echo "Retrying creating logstash user...";
  sleep 10;
done;

echo "All done!";

# Giữ container sống (nếu cần debug) hoặc exit
tail -f /dev/null