[ru](https://github.com/steqa-cashcache/repetition-service) [en](https://github.com/steqa-cashcache/.github/blob/main/locale/repetition-service/README.en.md)

# CashCache :coin: Repetition Service
:warning: Этот репозиторий является частью проекта [CashCache](https://github.com/steqa-cashcache) :warning:  
Для информации по установке и запуску см. [основной README](https://github.com/steqa-cashcache)


![GitHub Release](https://img.shields.io/github/v/release/steqa-cashcache/repetition-service)
![License](https://img.shields.io/badge/license-MIT-green)


![Java](https://img.shields.io/badge/Java-f58312.svg?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6db240?style=flat&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-00694a.svg?style=flat&logo=mongodb&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-ff6404.svg?style=flat&logo=rabbitmq&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ed?logo=docker&logoColor=white)
![gRPC](https://img.shields.io/badge/gRPC-2ca6af.svg?style=flat&logo=grpc&logoColor=white)


## Содержание
1. [Описание](#описание)
2. [Возможности](#возможности)
3. [gRPC Взаимодействие](#grpc-взаимодействие)
4. [Схема Хранения Данных](#схема-хранения-данных)
5. [Лицензия](#лицензия)


## Описание
Микросервис для управления регулярными транзакциями.  
Правила повторений хранятся в MongoDB, задачи запускаются и управляются через RabbitMQ.  
После выполнения задачи отправляется gRPC-запрос в [api](https://github.com/steqa-cashcache/api) для создания транзакции.


## Возможности
- Управление правилами регулярных транзакций (создание, удаление)
- Поддержка нескольких режимов повторений:
  - `FIXED_MONTH` — повтор каждый месяц в указанный день
  - `FIXED_YEAR` — повтор каждый год в указанный день и месяц
  - `INTERVAL_DAY` — повтор каждые N дней
  - `INTERVAL_SECONDS` — повтор каждые N секунд
- Создание задач для очереди RabbitMQ для выполнения по расписанию
- Вызов gRPC методов [api](https://github.com/steqa-cashcache/api) для создания транзакций на основе правил


## gRPC Взаимодействие

repetition-service предоставляет gRPC сервис `RepetitionService`, который используется REST API для добавления и удаления правил регулярных операций.

[api](https://github.com/steqa-cashcache/api) вызывает методы `AddRule` и `DeleteRule` для управления правилами регулярных операций.

Этот сервис при выполнении задачи читает правило из MongoDB, обновляет поле `nextExecution` согласно параметрам, создает новую задачу в RabbitMQ, и отправляет gRPC-запрос в [api](https://github.com/steqa-cashcache/api) для создания транзакции.


## Схема Хранения Данных
Правила повторений хранятся в MongoDB в коллекции, например, так:

```json
{
  "_id": {"$oid": "6852e79aede3047b341e706a"},
  "_class": "ru.steqa.repetitionservice.scheme.rabbit.repetition.IntervalSecondRepetition",
  "deleted": false,
  "mode": "INTERVAL_SECOND",
  "nextExecution": {"$date": "2025-06-18T16:22:45.000Z"},
  "seconds": 60,
  "transactionId": 1,
  "transactionType": "DEFAULT",
  "userId": 1
}
```

Правила имеют разные параметры в зависимости от `mode`:
- Для `FIXED_MONTH` — хранится только `day`.
- Для `FIXED_YEAR` — `day` и `month`.
- Для `INTERVAL_DAY` — количество дней (`days`).
- Для `INTERVAL_SECOND` — количество секунд (`seconds`).


## Лицензия
Этот проект распространяется под лицензией MIT. Подробнее см. в файле [LICENSE](LICENSE).
