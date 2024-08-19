## Описание проекта

Java Spring Boot Starter для логирования данных о запросах и ответах сервера

## Используемые технологии

- Spring Web
- Spring Boot Starter
- Log4j2

## Логирование
Для логирования был создан фильтр [LoggingFilter.java](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Ffilter%2FLoggingFilter.java),
Для настройки логирования используются такие свойства как:
- vashchenko.logging.enabled - булевое значение, отвечающее за включение/отключение фильтра
- vashchenko.logging.vashchenko.logging.include-request-body - булевое значение, отвечающее за включение в лог тело запроса
- vashchenko.logging.vashchenko.logging.include-request-headers - булевое значение, отвечающее за включение в лог заголовков запроса
- vashchenko.logging.vashchenko.logging.include-response-body - булевое значение, отвечающее за включение в лог тело ответа
- vashchenko.logging.vashchenko.logging.include-response-headers - булевое значение, отвечающее за включение в лог заголовков ответа
- vashchenko.logging.vashchenko.pattern - паттерн для логов
- vashchenko.logging.ignored-urls - список url-ов, к которым не применять логирование (указывать через запятую)


Log4j2 сконфигурирован так, что при запуске ему создается отдельный консольный аппендер с паттерном из свойств или по умолчанию, куда выводятся логи

### Пример логов от LoggingFilter
```
2024-08-19 23:22:36.225 [http-nio-8080-exec-1] INFO  com.vashchenko.education.t1.task2.filter.LoggingFilter - URL : "/api/v1/users", METHOD : "POST"
  REQUEST HEADERS: [content-type:"application/json", user-agent:"PostmanRuntime/7.41.1", accept:"*/*", postman-token:"4d1673e8-1f11-442f-b896-4abf4e455119", host:"localhost:8080", accept-encoding:"gzip, deflate, br", connection:"keep-alive", content-length:"44",]
  REQUEST BODY:
{
  "name" : "alex",
  "email" : "null"
}
  STATUS: "201"
  RESPONSE BODY:
{
  "status" : 201,
  "data" : {
  "id" : "6e495547-b36c-44fe-b0fa-deff403c47d9",
  "email" : "null",
  "name" : "alex"
  }
}
  RESPONSE HEADERS: [Content-Type:"application/json", Content-Length:"96", Date:"Mon, 19 Aug 2024 20:22:36 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive",]
  DURATION : 97 ms
```

## Структура проекта [task2](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2)[]()
- [analyzer](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Fanalyzer) - пакет, содержащий в себе анализатор ошибок при инициализации контекста
- [configuration](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Fconfiguration) - пакет, содержащий класс автоконфигурации с бинами и методами инициализации
 - [properties](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Fconfiguration%2Fproperties) - пакет, содержащий свойства стартера
- [exception](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Fexception) - пакет, содержащий ошибки, которые могут возникнуть при работы со стартером
- [filter](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Ffilter) - пакет содержащий фильтр для логирования
- [initialization](src%2Fmain%2Fjava%2Fcom%2Fvashchenko%2Feducation%2Ft1%2Ftask2%2Finitialization) - пакет, содержащий постпроцессор для обработки свойств стартера

## Инструкция по запуску
- Собираем проект в jar
```bash sh
mvn clean install
```
- Добавляем стартер в свой локальный репозиторий maven
``` bash sh
mvn install
```
- Включаем в свой проект
```
  <dependency>
    <groupId>com.vashchenko.education.t1</groupId>
    <artifactId>task2-spring-boot-starter-httplogging</artifactId>
    <version>0.0.1</version>
  </dependency>
```
## Файл с метаданными стартера
[additional-spring-configuration-metadata.json](src%2Fmain%2Fresources%2FMETA-INF%2Fadditional-spring-configuration-metadata.json)

## TODO
- написать тесты для фильтра

