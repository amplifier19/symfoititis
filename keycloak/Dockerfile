FROM quay.io/keycloak/keycloak:24.0.1 AS base 

FROM base AS dev-image
COPY --from=themes ./custom-keycloak-themes-24.0.1.jar /opt/keycloak/lib/lib/main/org.keycloak.keycloak-themes-24.0.1.jar
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]

FROM base AS builder 
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:24.0.1 AS stage-image
COPY --from=builder /opt/keycloak/ /opt/keycloak/
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized"]
