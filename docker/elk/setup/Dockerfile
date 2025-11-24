ARG STACK_VERSION
FROM docker.elastic.co/elasticsearch/elasticsearch:${STACK_VERSION}

# Thiết lập user root (User 0) để có quyền chown/chmod
USER 0

# Copy script vào
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

HEALTHCHECK --interval=1s --timeout=5s --retries=120 \
  CMD test -f config/certs/es01/es01.crt || exit 1

USER 0

ENTRYPOINT ["/entrypoint.sh"]