FROM postgis/postgis:18-3.6-alpine

# Копируем init-скрипты
COPY init/ /docker-entrypoint-initdb.d/

RUN chmod +x /docker-entrypoint-initdb.d/*.sh