-- Используем отличные от основных данных идентификаторы,
-- чтобы избежать конфликтов при инициализации БД в тестах.
INSERT INTO EMPLOYEE (id, full_name, email, department_id, manager_id, status, position, version) VALUES
  (100, 'Test User', 'test@example.com', 100, null, 'ACTIVE', 'Tester', 0),
  -- Сотрудник с идентификатором 1 для интеграционных тестов
  (1, 'Ash Test', 'ash@example.com', 100, null, 'ACTIVE', 'Tester', 0);

INSERT INTO DEPARTMENT (id, name, parent_dept_id, version) VALUES
  (100, 'Test', null, 0);

INSERT INTO USER_ACCOUNT (username, password_hash, role, version) VALUES
  ('test_admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoOe0HSfQ/ee5Q.fCF8dEY1Z6CD9GQbeeS', 'ADMIN', 0);