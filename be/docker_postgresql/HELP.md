# 📦 PostgreSQL через Docker Compose — Актуальный гайд

## 📁 Структура проекта

```
backend/
  .env                     ← основной файл переменных
  postgresql-docker-compose.yml
  docker_postgresql/
    postgres/
```

---

## ⚙️ Настройка `.env`

Создайте файл `.env` в **корне проекта (`backend/`)**:

```
ENV=dev

DB_USER=postgres
DB_PASSWORD=your_password_here
DB_HOST=localhost
DB_PORT=5432
DB_NAME=movio_main_db

DB_PORT_TEST=5433
DB_NAME_TEST=movio_test_db
```

### ⚠️ Важно:

* Используется **один общий `.env` файл**
* Не храните реальные пароли в репозитории
* `.env` автоматически подхватывается Docker Compose при запуске из корня проекта

---

## 🐳 Как это работает

В проекте поднимаются **2 контейнера PostgreSQL**:

| Контейнер     | Назначение  | Порт |
| ------------- | ----------- | ---- |
| postgres      | основная БД | 5432 |
| postgres_test | тестовая БД | 5433 |

---

## 🚀 Запуск

### 1. ✅ Убедитесь, что запущен Docker Desktop

---

### 2. Перейдите в корень проекта:

```
cd backend
```

---

### 3. Запустите контейнеры:

```
docker compose -f docker_postgresql/docker-compose.yml up --build -d
```

---

## 🛑 Остановка

```
docker compose -f docker_postgresql/docker-compose.yml down
```

---

## 🧹 Полная очистка (включая данные)

```
docker compose -f docker_postgresql/docker-compose.yml down -v
```

⚠️ Это удалит все данные баз данных

---

## 🔌 Подключение к базе данных

Используйте значения из `.env`:

**Host:** `localhost`

### Основная БД

* Port: `5432`
* Database: `movio_main_db`

### Тестовая БД

* Port: `5433`
* Database: `movio_test_db`

**User:** `postgres`
**Password:** (значение из `.env`)

---

## 💡 Важные моменты

* `.env` должен находиться **в корне проекта**, а не в `docker_postgresql`
* Запуск должен выполняться **из корня (`backend/`)**
* Docker Compose использует `.env` для подстановки переменных `${...}`

---

## 📌 Итог

* один `.env` → в корне проекта
* один compose → в `docker_postgresql`
* запуск → из корня

```
docker compose -f docker_postgresql/docker-compose.yml up --build -d
```

---
