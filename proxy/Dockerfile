FROM nginx:1.25.4-alpine as base
COPY ./config/proxy_params /etc/nginx/proxy_params
COPY ./html/ /usr/share/nginx/html/

FROM base as dev-image
COPY ./config/dev/nginx.conf /etc/nginx/nginx.conf
COPY ./config/dev/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

FROM base as stage-image
COPY ./config/stage/cert/localhost.crt /etc/ssl/certs/localhost.crt
COPY ./config/stage/cert/localhost.key /etc/ssl/private/localhost.key
COPY ./config/stage/nginx.conf /etc/nginx/nginx.conf
COPY ./config/stage/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80 
EXPOSE 443

FROM base as prod-image
COPY ./config/prod/cert/fullchain.pem /etc/ssl/certs/fullchain.pem
COPY ./config/prod/cert/privkey.pem /etc/ssl/private/privkey.pem
COPY ./config/prod/nginx.conf /etc/nginx/nginx.conf
COPY ./config/prod/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80 
EXPOSE 443
