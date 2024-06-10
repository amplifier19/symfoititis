FROM quay.io/keycloak/keycloak:24.0.1
WORKDIR /opt/keycloak
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://localhost:5432/keycloak
ENV KC_DB_USERNAME=symfoititis
ENV KC_DB_PASSWORD=postgresql3579
ENV KC_HOSTNAME=localhost
ENV KC_PROXY_HEADERS=xforwarded
ENV KC_HTTP_RELATIVE_PATH=/auth
ENV KC_FEATURES=token-exchange

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]
