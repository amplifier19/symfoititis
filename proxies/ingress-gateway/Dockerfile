FROM alpine:3.18 AS base

ENV LUA_VERSION=5.1
ENV LUA_PACKAGE=lua${LUA_VERSION}
ENV LUAROCKS_VERSION=5.1 
ENV NGINX_PATH=/usr/local/openresty/nginx 

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

COPY ./config/proxy_params ${NGINX_PATH}/conf/
COPY ./html/ ${NGINX_PATH}/html/


FROM base AS dev-image
COPY ./config/dev/nginx.conf ${NGINX_PATH}/conf/ 
EXPOSE 80


FROM base AS stage-image
COPY ./config/stage/cert/localhost.crt /etc/ssl/certs/localhost.crt
COPY ./config/stage/cert/localhost.key /etc/ssl/private/localhost.key
COPY ./config/stage/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80 
EXPOSE 443


FROM base AS prod-image
COPY ./config/prod/cert/fullchain.pem /etc/ssl/certs/fullchain.pem
COPY ./config/prod/cert/privkey.pem /etc/ssl/private/privkey.pem
COPY ./config/prod/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80 
EXPOSE 443
