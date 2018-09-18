FROM node:alpine as builder
WORKDIR /app
RUN apk add yarn
COPY ./ ./
RUN yarn install
RUN yarn build

FROM nginx
EXPOSE 4200
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html