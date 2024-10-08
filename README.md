# Приложение для погрузки посылок и подсчета их числа (REST/Shell/Tg)
В текущей версии взаимодействие с пользователем происходит тремя способами:
- при помощи Spring Shell
- с использованием REST API
- через телеграм бот

## Алгоритмы упаковки
- optimized - плотная упаковка в минимально возможное число грузовиков
- steady_bidirectional - равномерная упаковка в заданное число грузовиков

Если не удается распределить посылки по заданному числу грузовиков в соответствии с алгоритмом, бросается исключение AppException. 

## Команды Shell

### Команды Shell по управлению типами посылок

#### all-parcel-types

Выводит все доступные типы посылок в консоль

#### get-parcel-type --name typeName

Выводит описание типа с заданным именем

#### create-parcel-type --name typeName --shape typeShape --symbol typeSymbol

Создает тип с заданными параметрами (имя должно быть уникальным)

#### update-parcel-type --name typeName <--newName newName> <--newShape newShape> <--newSymbol newSymbol>

Обновляет тип с заданным именем

#### delete-parcel-type --name typeName

Удаляет тип с заданным именем

#### Примечание

Параметр Shape указывается как строка, в которой уровни (слои) посылки разделены запятыми. При этом пустоты обозначаются пробелами. Символ, который используется при создании формы, неважен: можно даже использовать разные. Символ типа передается как отдельный параметр. Пример валидного параметра формы:
```
--shape "kkk,k k,kkk"
```
Он задает посылку вида:
```
kkk
k k
kkk
```
Где k может быть любым символом, переданным в параметре --symbol

### Команды Shell по разбору грузовиков

#### scan-trucks --file fileName

Считает посылки в грузовиках из заданного файла в формате json

### Команды Shell по загрузке гузовиков

#### load-trucks --width truckWidth --height truckHeight --quantity truckQuantity --algorithm OPTIMIZED/STEADY --parcelsFromFile true/false --parcelsByForm true/false --parcelIn input --out output

Загрузка посылок в грузовики одинакового размера. Параметр parcelIn трактуется в зависимости от bool параметра parcelsFromFile. Если тот установлен в true, то parcelIn трактуется как имя файла. Также можно выбрать, в каком виде посылки хранятся в файле: по названию или по форме. Если parcelsFromFile установлен в false, тогда parcelIn воспринимается как строка имен, а параметр parcelsByForm игнорируется

#### load-trucks-customized --algorithm OPTIMIZED/STEADY --truckShapesFromFile true/false --parcelsFromFile true/false --parcelsByForm true/false --parcelIn parcelInput --truckShapesIn truckShapesInput --out output

Делает то же, что и load-trucks, только пользователь настраивает размеры каждого грузовика вручную. Размеры эти могут храниться файле или передаваться в виде строки

## Точки доступа REST

### Эндпоинты по управлению типами посылок

#### GET /truck-app/parcel-types

Параметры: -

Тело запроса: -

Описание: ищет все типы посылок

Возвращаемое значение: список типов посылок в формате json

#### POST /truck-app/parcel-types

Параметры: -

Тело запроса: создаваемый тип в формате json

Описание: создает новый тип посылок

Возвращаемое значение: созданный тип в формате json

Пример запроса:
```
POST http://localhost:8080/truck-app/parcel-types
Content-Type: application/json

{
  "name": "my",
  "shape": [
    [
      true,
      true
    ],
    [
      true,
      false
    ],
    [
      true,
      true
    ]
  ],
  "symbol": "s"
}
```

#### GET /truck-app/parcel-types/{name}

Параметры: имя типа

Тело запроса: -

Описание: ищет тип посылок с заданным именем

Возвращаемое значение: тип с заданным именем в формате json или пустое тело + код 404, если такого типа нет

#### PATCH /truck-app/parcel-types/{name}

Параметры: имя типа

Тело запроса: сущность, которая содержит изменяемые поля, в формате json

Описание: изменяет тип посылок с заданным именем

Возвращаемое значение: измененный тип в формате json

#### DELETE /truck-app/parcel-types/{name}

Параметры: имя типа

Тело запроса: -

Описание: удаляет тип посылок с заданным именем

Возвращаемое значение: true или false в зависимости от того, была ли сущность удалена

### Эндпоинты по разбору грузовиков (подсчету числа посылок)

#### POST /truck-app/truck-scanning

Параметры: -

Тело запроса: файл json с грузовиками, закодированный в base64 (строка)

Описание: осуществляет подсчет посылок в грузовиках

Возвращаемое значение: грузовики с посчитанными посылками в формате json

### Эндпоинты по заполнению грузовиков

#### POST /truck-app/truck-loading/default

Параметры: -

Тело запроса: объект json, содержащий всю ту же информацию, что и параметры аналогичного Shell метода

Описание: грузит посылки в одинаковые грузовики

Возвращаемое значение: список заполненных грузовиков

Пример запроса:

```
POST http://localhost:8080/truck-app/truck-loading/default
Content-Type: application/json

{
  "width": 6,
  "height": 6,
  "quantity": 5,
  "algorithm": "OPTIMIZED",
  "parcelsFromFile": false,
  "parcelsByForm": true,
  "parcelIn": "777\n7777\n\n333",
  "out": "tmp/res.json"
}
```

#### POST /truck-app/truck-loading/customized

Параметры: -

Тело запроса: объект json, содержащий всю ту же информацию, что и параметры аналогичного Shell метода

Описание: грузит посылки в грузовики кастомизированых размеров (3x7 и тд)

Возвращаемое значение: список заполненных грузовиков

```
POST http://localhost:8080/truck-app/truck-loading/customized
Content-Type: application/json

{
  "algorithm": "STEADY",
  "truckShapesFromFile": true,
  "parcelsFromFile": false,
  "parcelsByForm": false,
  "truckShapesIn": "Nng2LDR4Mw==",
  "parcelIn": "7,5,3",
  "out": "tmp/res.json"
}
```
Примечание: если в передаваемом объекте указано, что какой-либо параметр берется из файла, ожидается, что соответствующее поле объекта будет хранить не имя файла, а его содержимое, закодированное в base64. В данном случае это truckShapesIn

## Команды бота

Они совпадают с аналогичными эндпоинтами Shell, однако вместо отдельных параметров отправляются объекты json, как в REST (для упрощения парсинга).

Пример команды обновления типа посылок:

```
/update_parcel_type my 1 {"name": "my 2","shape": [[true,false],[true,false],[true,true]],"symbol": "m"}
```
Здесь 'my 1' - это имя обновляемого типа, а дальше следует json объект, содержащий обновляемые поля

Команды /load_trucks и /load_trucks_customized могут также принимать файл с типами посылок или их именами на вход, а команда /scan_trucks всегда принимает файл с заполненными грузовиками

Команды /load_trucks и /load_trucks_customized в ответ всегда отсылают пользователю файл с заполенными грузовиками

Пример команды бота, к которой обязательно должен прилагаться файл (тк parcelsFromFile=true):
```
/load_trucks {
  "width": 6,
  "height": 6,
  "quantity": 5,
  "algorithm": "OPTIMIZED",
  "parcelsFromFile": true,
  "parcelsByForm": true,
  "out": "tmp/res.json"
}
```



