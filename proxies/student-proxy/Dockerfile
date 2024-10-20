FROM alpine:3.18 AS base

ARG LUA_VERSION
ARG LUA_PACKAGE
ARG LUA_MODULES_PATH 
ARG NGINX_PATH

# install lua, luajit, luarocks
RUN apk add build-base git bash zip unzip curl
RUN apk add ${LUA_PACKAGE}
RUN apk add ${LUA_PACKAGE}-dev
RUN apk add luajit
RUN apk add luarocks
RUN ln -s /usr/bin/luarocks-$LUA_VERSION /usr/bin/luarocks

# install nginx lua-modules for openid support
RUN luarocks install lua-resty-openidc
RUN luarocks install lua-resty-http
RUN luarocks install lua-resty-session
RUN luarocks install lua-resty-jwt

# install openresty
COPY ./openresty.sh ./
RUN chmod u+x openresty.sh
RUN ./openresty.sh

# update PATH
RUN echo 'PATH=${PATH}:/usr/local/openresty/nginx/sbin' >> /etc/profile

# lua modules
RUN mkdir $LUA_MODULES_PATH
COPY --from=lua-modules ./roles.lua $LUA_MODULES_PATH

EXPOSE 80


FROM base AS dev-image
COPY ./config/dev/nginx.conf ${NGINX_PATH}/conf/


FROM base AS stage-image
COPY ./config/stage/nginx.conf ${NGINX_PATH}/conf/


FROM base AS prod-image
COPY ./config/stage/nginx.conf ${NGINX_PATH}/conf/
