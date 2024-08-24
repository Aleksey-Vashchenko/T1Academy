## Описание проекта

Простое Java Spring Boot приложение, использующее JWT для авторизированного доступа к ресурсам
## Используемые технологии

- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker
- H2 database
- JSON Web Token

## Security
Были созданы 3 эндпоинта:
- /api/v1/resource/public - публичный, доступ к которому имеют неавторизированные и авторизированные пользователи с любой ролью
- /api/v1/resource/authenticated - аунтентифицированный, доступ к которому имеют доступ только авторизированные пользователи с любой ролью
- /api/v1/resource/private - приватный, доступ к которому имеет только пользователь с ролью Admin

Для проверки токенов используется [JwtFilter.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fsecurity%2FJwtFilter.java), который порверяет наличие
токена в заголовке и при успешной его проверке добавляет в SecurityContextHolder [JwtAuthentication.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fsecurity%2FJwtAuthentication.java)

Для обновления Access токена в базе данных хранится Refresh Token, привязанный к выданному вместе с ним хешу Access токена.
При наличии переданного Refresh токена и верного к нему хеша Access токена будет выдана новая пара токенов

## Структура проекта [task_3](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3)
- [security](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fsecurity) - пакет, содержащий в себе конфигурацию Spring Security
- [exception](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fexception) - пакет, содержащий базовые Runtime исключения, генерируемые сервисным слоем
- [entity](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fmodel%2Fentity)[entity](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel%2Fentity) - основные сущности проекта
- [repository](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Frepository) - пакет, содержащий интерфейсы наследники JPA репозиториев для взаимодействия сущностей с базой данных
- [service](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fservice) - пакет, содержащий сервисный слой приложения
- [web](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_3%2Fweb)[controllers](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fweb%2Fcontrollers) - пакет, содеражщий MVC контроллеры для взаимодействия с клиентами и DTO для запросов и ответов


## Инструкция по запуску
- Запуск через docker-compose 
```bash sh
docker-compose up --build
```
или можете включить в свой compose файл данный [образ](https://hub.docker.com/repository/docker/alekseyvashchenko/t1_task_3) 
- Запуск, если установлен PostgreSQL, передав нужные параметры для подключения к бд

Сборка проекта в jar файл
``` sh
./mvnw clean install --% -Dmaven.test.skip=true
```
Запуск jar файла с передачей параметров под вашу базу данных
```sh
java -jar ./target/Task_1-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/postgres --spring.datasource.username=test --spring.datasource.password=test --spring.jpa.properties.hibernate.default_schema=public
```

Коллекция запросов Postman [T1 academy.postman_collection.json](T1%20academy.postman_collection.json)

