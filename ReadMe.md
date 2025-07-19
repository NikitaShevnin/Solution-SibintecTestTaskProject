
````markdown
# Сервис учёта рабочего времени

Этот микросервис — решение тестового задания.  
По сути, это простая система для трекинга рабочего времени сотрудников.  
Сделано на Spring Boot, данные хранятся во встроенной H2 базе.

## Как собрать и запустить

1. Сначала собираешь проект через Maven:

  ```bash
  mvn clean package
  ```

2. Затем запускаешь приложение:

  ```bash
  mvn spring-boot:run
  ```

По дефолту сервис стартует на порту `8080`.
Swagger-документация будет доступна тут:
`http://localhost:8080/swagger-ui/index.html`

Более детальная инфа по REST — в файле `SwaggerOpenAPIDock`.
````

---

## Основные эндпоинты

Вот список базовых точек API:

1. `POST /api/auth/register` — регистрация нового пользователя.
2. `POST /api/employees` — создать сотрудника.
3. `GET /api/employees` — получить список всех сотрудников.
4. `POST /api/time-entries` — добавить запись о времени работы.
5. `GET /api/time-entries` — получить список всех записей.
6. `GET /api/reports/monthly` — месячный отчёт (можно получить как PDF).
7. `GET /api/reports/project` — отчёт по проекту (PDF).
8. `GET /api/reports/absence` — отчёт по пропускам (PDF).

---

## Пример добавления времени (через Postman)

1. Создай `POST` запрос на
   `http://localhost:8080/api/time-entries`

2. В Headers добавь:
   `Content-Type: application/json`

3. В тело запроса передай JSON:

   ```json
   {
     "employeeId": 1,
     "date": "2025-07-14",
     "hoursWorked": 8
   }
   ```

---

## Примеры запросов в Postman

### ✅ Регистрация юзера

1. `POST http://localhost:8080/api/auth/register`
2. Заголовок: `Content-Type: application/json`
3. Тело запроса:

   ```json
   {
     "username": "user1",
     "passwordHash": "secret",
     "role": "EMPLOYEE"
   }
   ```

---

### 🧑‍💻 Создание сотрудника

1. `POST http://localhost:8080/api/employees`
2. Заголовок: `Content-Type: application/json`
3. JSON:

   ```json
   {
     "fullName": "Иван Иванов",
     "email": "ivan@example.com",
     "departmentId": 1,
     "status": "ACTIVE",
     "position": "Developer"
   }
   ```

---

### 📋 Получить список сотрудников

* Просто сделай `GET` запрос:
  `http://localhost:8080/api/employees`

---

### ⏱ Добавить запись о времени

1. `POST http://localhost:8080/api/time-entries`
2. Заголовок: `Content-Type: application/json`
3. Тело:

   ```json
   {
     "employeeId": 1,
     "date": "2025-07-14",
     "hoursWorked": 8,
     "projectCode": "PRJ1"
   }
   ```

---

### 🔍 Получить все записи о времени

* `GET http://localhost:8080/api/time-entries`

---

### 📊 Месячный отчёт (JSON)

1. `GET http://localhost:8080/api/reports/monthly`
2. Заголовок: `Accept: application/json`

---

### 📈 Отчёт по проектам (CSV)

1. `GET http://localhost:8080/api/reports/project`
2. Заголовок: `Accept: text/csv`
3. Сохрани полученный файл

---

### ❌ Отчёт по пропускам (PDF)

1. `GET http://localhost:8080/api/reports/absence`
2. Заголовок: `Accept: application/pdf`
3. Скачай PDF

---

## ⚙️ Запуск тестов

Выполнить:

```bash
mvn test
```

Тесты используют профиль `test` (лежит в `src/test/resources/application-test.yml`).
Для начальных данных используется файл:
`src/test/resources/test-data.sql`

Проверяется, что контекст Spring поднимается корректно.

---

## 🔒 Безопасность и многопоточка

Чтобы всё стабильно работало в параллельных запросах, в проекте реализовано следующее:

1. У всех сущностей включена версионность (`@Version`) — защищает от конфликтов при обновлении.
2. Время по одному сотруднику и дате может быть только одно — стоит уникальный индекс.
3. `TimeEntryService` работает с уровнем изоляции `SERIALIZABLE`, и перед добавлением проверяет, нет ли уже записи.
4. Все чтения помечены как `readOnly` — меньше блокировок, быстрее работает.

Так же, приложение умеет работать с конкурентным доступом и не ломается под нагрузкой.

---