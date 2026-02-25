# Hotel Reservation API

REST API для управления отелями: создание, поиск, добавление удобств и получение статистики.

## Технологии

- Java 17
- Spring Boot 4.0.2
- Spring Data JPA + Liquibase
- H2 (in-memory, режим PostgreSQL) — по умолчанию
- MySQL — поддерживается (коннектор включён)
- Swagger / OpenAPI (springdoc)
- Lombok

## Запуск

### Требования

- Java 17+
- Maven 3.6+
- Внешняя база данных **не нужна** — используется H2 in-memory

### Шаги

1. Клонировать репозиторий:
   ```bash
   git clone <url>
   cd TaskGP
   ```

2. Запустить приложение:
   ```bash
   mvn spring-boot:run
   ```

Приложение запустится, Liquibase автоматически создаст схему БД.

### Доступные адреса

| Адрес | Описание |
|-------|----------|
| http://localhost:8092/property-view/hotels | REST API |
| http://localhost:8092/swagger-ui.html | Swagger UI |
| http://localhost:8092/v3/api-docs | OpenAPI JSON |
| http://localhost:8092/h2-console | H2 консоль (JDBC URL: `jdbc:h2:mem:hotelreservation`) |

## Сборка и тесты

```bash
# Сборка
mvn clean package

# Запуск всех тестов
mvn test

# Запуск одного теста
mvn test -Dtest=HotelreservationApplicationTests#shouldCreateAndReturnHotel
```

## API

Базовый путь: `/property-view`

| Метод  | Путь                          | Описание                                      |
|--------|-------------------------------|-----------------------------------------------|
| GET    | `/hotels`                     | Список всех отелей (краткая информация)       |
| GET    | `/hotels/{id}`                | Детали отеля по ID                            |
| GET    | `/search`                     | Поиск по name, brand, city, country, amenities |
| POST   | `/hotels`                     | Создать отель                                 |
| POST   | `/hotels/{id}/amenities`      | Добавить удобства к отелю                     |
| GET    | `/histogram/{param}`          | Количество отелей по полю (city, brand, country, amenities) |

### Пример создания отеля

```http
POST /property-view/hotels
Content-Type: application/json

{
  "name": "Grand Hotel",
  "brand": "Luxury",
  "description": "5-star hotel in the city center",
  "address": {
    "country": "Belarus",
    "city": "Minsk",
    "street": "Nezavisimosti",
    "houseNumber": "1",
    "postCode": "220000"
  },
  "contacts": {
    "phone": "+375291234567",
    "email": "info@grandhotel.by"
  },
  "arrivalTime": {
    "checkIn": "14:00",
    "checkOut": "12:00"
  }
}
```

### Пример ответа об ошибке

```json
{ "error": "Hotel with id 999 not found" }
```

## Структура проекта

```
src/main/java/com/example/hotelreservation/
├── api/              # REST-контроллеры и DTO
├── application/      # Facade, Service, Mapper
├── domain/           # JPA-сущности и репозитории
├── common/           # Глобальная обработка ошибок
└── config/           # OpenAPI, Liquibase конфигурация

src/main/resources/
├── application.properties
└── db/changelog/     # Liquibase-миграции
```
