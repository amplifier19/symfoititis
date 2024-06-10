FROM node:iron-alpine as base
WORKDIR /opt/app

FROM base as modules
COPY ./package.json .
COPY ./package-lock.json .
RUN npm install

FROM base as stage-build
COPY ./ /opt/app
COPY --from=modules /opt/app ./
RUN npm run build staging

FROM base as prod-build
COPY ./ /opt/app
COPY --from=modules /opt/app ./
RUN npm run build production

FROM nginx:1.25.4-alpine as stage-image
COPY --from=stage-build /opt/app/dist /usr/share/nginx/html
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

FROM nginx:1.25.4-alpine as prod-image
COPY --from=prod-build /opt/app/dist /usr/share/nginx/html
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf