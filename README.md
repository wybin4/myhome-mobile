# myhome-mobile

## Описание
Этот репозиторий содержит мультимодульное мобильное приложение проекта MyHome, разработанное под Android на Kotlin. Проект взаимодействует с соответствующим [бэкендом](https://github.com/wybin4/myhome).

## Структура
Проект состоит из двух модулей - data и presentation. Каждый модуль разделяется по features на аналогичные группы.

## presentation

### `appeal`

- `list` - список обращений
- `pick` - выбор типа обращения для добавления
- `add` - add, verify, problem и claim

Схема переходов list -> pick -> add, add -> list

### `charge`

- `list` - список начислений с графиком MPAndroidChart
- `get` - детали начисления с возможностью скачать квитанцию и оплатить
- `pay` - страница оплаты, с неё переход на сайт ЮMoney

Схема переходов list -> get -> pick и обратно
  
### `chat` на сокетах

- `list` - список чатов
- `add` - добавление нового чата
- `get` - сам чат. Работает realtime отправка/прочтение сообщений

Схема переходов list -> add -> get -> list, list -> get и обратно

### `common` - просто модели и конвертеры

### `event`

- `list` - список событий, в нем голосования и домовые уведомления

### `meter`

- `list` - список счетчиков по квартирам
- `get` - просмотр детальной информации о счетчике
- `add` - добавление счетчика
- `update` - изменение даты поверки счетчика
- `scan` - добавление показаний

Схема переходов list -> get, list -> add -> list, get -> scan (update) -> get

### `servicenotifications`

- `list` - список сервисных уведомлений

## Детали

## Ресурсы
- Дизайн в [Figma](https://www.figma.com/file/KdgVnoLLvbCBwi2m9712JD/%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D0%BA%D0%B0?type=design&node-id=0%3A1&mode=design&t=rpPwzzwNdAsMZzMY-1)
