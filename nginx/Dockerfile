FROM nginx:latest

RUN rm /etc/nginx/conf.d/default.conf

COPY ./conf/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
EXPOSE 443

CMD ["nginx", "-g", "daemon off;"]
