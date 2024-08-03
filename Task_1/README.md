## Описание проекта

Простое Java Spring Boot приложение, использующее REST подход для создания пользователей, их заказов и управления ими. На старте создается пользователь и один заказ к нему (их uuid выводятся в консоль)

## Используемые технологии

- Spring Web
- Spring Data JPA
- Spring AOP
- PostgreSQL
- Docker
- Log4j2

## Логирование
Для логирования были созданы аннотации [@LogExceptionOnly.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop%2Fannotation%2FLogExceptionOnly.java) и [@LogMethodExecution.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop%2Fannotation%2FLogMethodExecution.java),
где первая отвечала только за логирование исключений, выбрасываемых помеченным методом, а вторая включала функционал предыдущей, при этом логируя еще аргументы метода и длительность выполнения. Аннотации применимы к любым методам, но я использовал их для логирования сервисного слоя. Для логирования запросов я бы использовал Filter.
Также, для подкрепления знаний, были созданы еще 2 аспекта и аннотаций
- [ValidationAspect.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop%2FValidationAspect.java) с аннотацией [@OrderType.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop%2Fannotation%2FOrderType.java) и  для валидации помеченного поля на соотвествие значениям из [OrderStatus.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel%2Fentity%2FOrderStatus.java)  при попадании DT объекта в методы класса помеченного @RestController
- [ApiResponseAspect.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop%2FApiResponseAspect.java) позволяющий ответы от методов контроллеров оборачивать в [ApiResponse.class](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel%2Fdto%2Fresponse%2FApiResponse.java) и брать статус из @ResponseStatus, но для этого пришлось умышленно сделать все методы Rest контроллеров не void

Log4j2 сконфигурирован так, что логи от ValidationAspect.java сохраняются отдельно в task1-logs/aop-annotations.log, а также файл application.log, куда сохраняются все логи приложения

### Пример логов от LoggingAspect
- 2024-08-03 17:12:32.672 [main] INFO  com.vashchenko.education.t1.task_1.aop.LoggingAspect [4672] - Method com.vashchenko.education.t1.task_1.service.dbImpl.JpaUserService.createUser called with parameters: [UserDto[id=null, email=leha.vashchenko@gmail.com, name=Lesha]]
- 2024-08-03 17:12:32.800 [main] INFO  com.vashchenko.education.t1.task_1.aop.LoggingAspect [4672] - Method com.vashchenko.education.t1.task_1.service.dbImpl.JpaUserService.createUser returned with result: UserDto[id=285d5962-55cb-4d43-b340-29c69350b587, email=leha.vashchenko@gmail.com, name=Lesha]


## Структура проекта [task_1](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1)
- [aop](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Faop) - пакет, содержащий в себе аспекты Spring AOP для логирования, конвертации и валидации, а также аннотации для их применения
- [exception](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fexception) - пакет, содержащий базовые Runtime исключения, генерируемые сервисным слоем
- [mapper](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmapper) - пакет, содержащий интерфейсы MupStruct мапперов для преобразований DT объектов в Entity объекты и наоборот
- [model](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel) - пакет, содержащий DT объекты и Entity объекты
 - [dto](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel%2Fdto) - DT объекты для приема данных и формирования ответов
 - [entity](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fmodel%2Fentity) - основные сущности проекта
- [repository](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Frepository) - пакет, содержащий интерфейсы наследники JPA репозиториев для взаимодействия сущностей с базой данных
- [service](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fservice) - пакет, содержащий сервисный слой приложения
- [controllers](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask_1%2Fweb%2Fcontrollers) - пакет, содеражщий MVC контроллеры для взаимодействия с клиентами


## Инструкция по запуску
- Запуск через Docker 
```bash sh
docker-compose up --build
```
- Запуск, если установлен PostgreSQL, передав нужные параметры для подключения к бд

Сборка проекта в jar файл
``` sh
./mvnw clean install --% -Dmaven.test.skip=true
```
Запуск jar файла с передачей параметров под вашу базу данных
```sh
java -jar ./target/Task_1-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/postgres --spring.datasource.username=test --spring.datasource.password=test --spring.jpa.properties.hibernate.default_schema=public
```


