## Консольное приложение для погрузки посылок и подсчета их числа
В текущей версии взаимодействие с пользователем происходит при помощи Spring Shell.

### Алгоритмы упаковки
- optimized - плотная упаковка в минимально возможное число грузовиков
- steady_bidirectional - равномерная упаковка в заданное число грузовиков

Если не удается распределить посылки по заданному числу грузовиков в соответствии с алгоритмом, бросается исключение AppException. 

### Команды по управлению типами посылок

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

### Команды по разбору грузовиков

#### scan-trucks --file fileName

Считает посылки в грузовиках из заданного файла в формате json

### Команды по загрузке гузовиков

#### load-trucks --width truckWidth --height truckHeight --quantity truckQuantity --algorithm OPTIMIZED/STEADY --parcelsFromFile true/false --parcelsByForm true/false --parcelIn input --out output

Загрузка посылок в грузовики одинакового размера. Параметр parcelIn трактуется в зависимости от bool параметра parcelsFromFile. Если тот установлен в true, то parcelIn трактуется как имя файла. Также можно выбрать, в каком виде посылки хранятся в файле: по названию или по форме. Если parcelsFromFile установлен в false, тогда parcelIn воспринимается как строка имен, а параметр parcelsByForm игнорируется

#### load-trucks-customized --algorithm OPTIMIZED/STEADY --truckShapesFromFile true/false --parcelsFromFile true/false --parcelsByForm true/false --parcelIn parcelInput --truckShapesIn truckShapesInput --out output

Делает то же, что и load-trucks, только пользователь настраивает размеры каждого грузовика вручную. Размеры эти могут храниться файле или передаваться в виде строки
