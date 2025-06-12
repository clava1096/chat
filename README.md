
# Чат-приложение на Spring Boot

## Описание проекта
Проект представляет собой веб-приложение для обмена сообщениями между пользователями с дополнительными функциями публикации постов. Реализованы следующие возможности:

- Регистрация и аутентификация пользователей
- Обмен сообщениями в реальном времени через WebSocket
- Публикация и управление постами
- GraphQL API для работы с постами

## Технологический стек

- Backend: Spring Boot 3, Spring Security, Spring Data JPA, Spring WebSocket
- База данных: PostgreSQL
- Аутентификация: JWT, OAuth Client 
- API: REST + GraphQL
- Документация: OpenAPI 3 (Swagger)

## Установка и запуск
1) Клонировать репозиторий:

```Bash
git clone https://github.com/clava1096/chat
cd chat-app
```

2) Настроить подключение к БД в application.yml:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/chatdb
    username: postgres
    password: yourpassword
```

3) Запустить приложение:
```bash
./mvnw spring-boot:run
```


# API Endpoints
```
Registration

POST /api/login - вход в систему (возвращает JWT токен)
POST /api/registration - регистрация нового пользователя

WebSocket

WebSocket endpoint: /ws
Отправка сообщений: /app/chat
Подписка на сообщения: /user/queue/messages

REST API для чата

GET /messages/{senderId}/{receiverId} - получить историю переписки
GET /messages/{senderId}/{receiverId}/count - количество непрочитанных сообщений
GET /messages/{id} - получить сообщение по ID

GraphQL API для постов

Query.searchPosts(keyword: String) - поиск постов
Query.getPost(id: UUID) - получить пост по ID
Query.getAllPosts - получить все посты
Mutation.createPost(input: PostInput) - создать новый пост
Mutation.updatePost(id: UUID, input: PostInput) - обновить пост
Mutation.deletePost(id: UUID) - удалить пост
```

## Документация API

Документация доступна по адресу:

http://localhost:8080/swagger-ui.html

http://localhost:8080/graphiql (для GraphQL)

