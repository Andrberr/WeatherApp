Погодное приложение, навигация осуществляется с помощью навигационного графа, содержащего некоторое количество фрагментов, которые являются экранами приложения. 
Реализация приложения включают в себя следующее:
1. Использование REST API для взаимодействия с сервером при помощи HTTP запросов, также Retrofit, который используется для выполнения этих запросов.
2. Использование Room для взаимодействия с базой данных, которая хранит информацию о погоде.
3. Использование инъекции зависимостей с помощью библиотеки Dagger 2 для упрощения взаимодействия между классами.
4. Использование базы данных Firebase для хранения всех городов.
5. Использования Yandex Maps для того, чтобы получать погоду по местоположению.
6. Использование View binding для простого взаимодействия с View.
В целом приложение позволяет просматривать погоду на определённое количество дней, текущую погоду, погоду по часам, добавление нескольких городов, удаление города, а также просмотр погоды исходя из местоположения пользователя.
