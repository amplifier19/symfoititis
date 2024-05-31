FROM nginx:1.25.4-alpine

COPY proxy_params /etc/nginx/proxy_params

COPY nginx.conf /etc/nginx/nginx.conf

COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
