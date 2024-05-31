FROM nginx:1.25.4-alpine

COPY ./dist /usr/share/nginx/html

COPY ./nginx/nginx.conf /etc/nginx/nginx.conf

COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
