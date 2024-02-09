# Тестовое задание для Lolzteam
## Requirements:
* Java 17 или выше, можно скачать здесь - https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe
* PostgreSQL база данных ( рекомендую использовать pgAdmin4 для настройки базы данных ) https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
* Postman для тестирования веб-приложения https://dl.pstmn.io/download/latest/win64

## Что дальше?
* Установи pgAdmin4, используя настройки по умолчанию. ***ВАЖНО!*** Если вы запускаете приложение через jar, а не IDE, то при установке **требуется** создать пользователя **YOURUSERNAME** с паролем **YOURPASSWORD**.
> Если вы запускаете приложение через IDE, то пропишите в application.properties данные от пользователя.
* Создайте базу данных в pgAdmin4. ***ВАЖНО!*** Если вы запускаете приложение через jar, а не IDE, то необходимо создать базу данных с названием **zelenka_guru**.
> Если вы запускаете приложение через IDE, то пропишите в application.properties ```spring.datasource.url=jdbc:postgresql://localhost:5432/db_name```, где db_name - это название вашей базы данных.

## Как запустить приложение?
* Если вы запускате через Jar:
> https://github.com/rosenyz/ELibraryForZelenkaGuru/assets/49805290/24e9a861-ace8-4199-873c-f9ceed56aa86
* Если вы запускаете через IDE, то проверьте корректность данных в application.properties

* По умолчанию приложение запускается на http://localhost:8080

## Как использовать?

### Get Mappings:
* ```/api/v1/books/{book_id}```, где ```book_id``` - id книги в базе данных

* ```/api/v1/books?search={s}&genre_id={u}&limit={k}&page={n}```, где ```s``` - ключевое слово/фраза для поиска книг по бд, ```u``` - id жанра добавленного пользователем, ```k``` - количество книг, приходящих в ответ JSON'ом, ```n``` - номер страницы.
> Все параметры выше необязательны.

* ```/api/v1/books/{book_id}/delete```

* ```/api/v1/genres/create?genre={s}```, где ```s``` - название жанра

* ```/api/v1/genres?limit={k}&page={n}```, аналогично с ```/api/v1/books```

### Post Mappings:
* ```/api/v1/auth/register```, принимает в себя обязательный JSON. Данные для JSON'а можно посмотреть в ```dtos\requests```

* ```/api/v1/auth/login```, принимает в себя обязательный JSON. Данные для JSON'а можно посмотреть в ```dtos\requests```. Возвращает JWT-токен для авторизации.

* ```/api/v1/books/add```, принимает в себя обязательный JSON. Данные для JSON'а можно посмотреть в ```dtos\requests```. 
> Запрос можно отправить только в fully authenticated состоянии
