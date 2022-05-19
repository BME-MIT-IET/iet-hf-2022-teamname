# Az alkalmazás hibáinak feltárása manuális teszteléssel

## Tesztelés menete:

- kezelőfelület tesztelése
- egyszerű eljárások tesztelése
- eljárás készítése
- több eljárás egybeágyazása
- eljárások exportálása, importálása
- loopok használata

## Hiba: Ablak átméretezése

Az ablak átméretezésénél ugyan automatikusan változik a rajztábla mérete, viszont emiatt a már megrajzolt vonalak ha így az ablakon kívülre kerülnek akkor azok eltűnnek, így az ablak eredeti méretének visszaállítását követően nem látszanak.

![](test-images/hiba1_1.png)
![](test-images/hiba1_2.png)
![](test-images/hiba1_3.png)

## Hiba: Eljárás nevében szám adható meg

A program használatának egyik szabálya, hogy az eljárás neve csak betűket tartalmazhat, azonban lehetőség van olyan függvényt is megadni ami nem csak betűkből áll. Ezeket meghívni nem lehet.

A hiba rekonstruálása:
- `eljárás létrehozása olyan névvel, ami számot is tartalmaz: func1`
- `eljárás meghívása`

![](test-images/hiba2.png)

## Hiba 3: Saját eljárás helytelen működése

A dokumentációban található szabályok szerint különböző karaktersorozatok esetén nem szükséges szóközt tenni az utasítások közé. A func eljárás esetén azonban szóköz nélkül nem működik jól a program, csak az első utasítás hajtódik végre a második paraméterrel:

![](test-images/hiba3_1.png)

A hiba rekonstruálása:
- `Functions -> Open tab -> Add Function`
- `eljárás elnevezése: func`
- `param1, param2 változók hozzáadása`
- `eljárás törzse: j$param1e$param2`
- `eljárás meghívása: func(30,100)`

Szóközzel a két utasítás között megfelelően működik az eljárás.

## Hiba 4: Eljárás importálásának sikertelensége

Eljárás importálásakor, ha sikertelen egy eljárás betöltése, a felhasználó nem kap visszajelzést erről. Sem a sikerességről/sikertelenségről sem a meghiúsult importálás okáról. Az ok lehet például: rossz formátum, már létező eljárás például más paraméterrel.

Hiba rekonstruálása:
- `Functions -> Open tab -> Add Function`
- `eljárás elnevezése: func`
- `param1, param2 változók hozzáadása`
- `eljárás törzse: j$param1 e$param2`
- `Functions -> Open tab -> Import Function`
- `json fájl betöltése, tartalma: [
  {
    "variables": [ "param3", "param2" ],
    "name": "func",
    "commandStr": "j$param1 e$param2"
  }
]`

Az importálás nem sikerül, és a program kivételt dob.