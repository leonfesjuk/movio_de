# 📦 PostgreSQL через Docker Compose — Быстрый гайд

## 📁 Структура

В проекте используется папка:

```
docker_postgresql/
```

Внутри должны находиться:

* `docker-compose.yml`
* `.env`

---

## ⚙️ Настройка `.env`

Создайте файл `.env` в папке `docker_postgresql` со следующим содержимым:

```
ENV=dev

DB_USER=postgres
DB_PASSWORD=movio2188
DB_HOST=localhost
DB_PORT=5432
DB_NAME=movio_web_main

DB_PORT_TEST=5433
DB_NAME_TEST=movio_web_test
```

### ⚠️ Важно:

* Будут подняты **2 контейнера базы данных**:

    * Основная БД → порт **5432**
    * Тестовая БД → порт **5433**

---

## 🚀 Запуск

### 1. ✅ Проверь, запущен ли Docker Desktop

### 2. Перейдите в папку:

```
cd .\docker_postgresql\
```

### 3. Запуск контейнеров:

```
docker compose up --build -d
```

---

## 🛑 Остановка

Обычная остановка:

```
docker compose down
```

---

## 🧹 Полная очистка (включая данные БД)

Если нужно полностью удалить контейнеры **и данные**:

```
docker compose down -v
```

---

## 🔌 Подключение к базе данных

Для подключения используйте значения из `.env`:

* Host: `localhost`
* Port:

    * `5432` — основная БД
    * `5433` — тестовая БД
* User: `postgres`
* Password: `movio2188`
* Database:

    * `movio_web_main`
    * `movio_web_test`

---

## 💡 Примечание

После удаления с флагом `-v` все данные баз будут безвозвратно удалены. Используйте эту команду только при необходимости полной очистки.
