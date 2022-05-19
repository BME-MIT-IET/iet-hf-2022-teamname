# SonarCloud statikus tesztek elvégzése

## Elvégzett munka
A SonarCloud eszköz segitségével rengeteg codesmellt és néhány bugot felderithettünk a programban.

Gyakori hibák a nem használt paraméterek, változók kódrészletek. Ezek fejlesztés során könnyen bent maradhatnak a kódban, a SoanrCloud
ezeket felderíti, így eltávolíthatjuk őket.

Az equals metódus kapcsán több probléma is felmerült:
A metódusban elmaradtak az alapvető típustesztek, amelyek elengedhetetlenek a biztos helyes működéshez, ezek implmentálásra kerültek.
Az equals metódus felüldefiniálásakor a hashCode metódust is felül kell definiálnunk, mivel a konvenció szerint azok az objektumok, amelyek az
equals szerint egyenlőek, ugyanazt a hash kódot kell produkálniuk a hashCode metódusban. így ezek a metódusok implmentálásra kerültek.

További probléma volt még, hogy nem történt megfelelő figyelemfordítás a lehetséges exception-ökre. Ezt try-catch blokkokk kódhoz adásával oldottuk meg.

A SonarCloud szerint érdemes és fontos az egyes változó- és metódusneveket úgy megválasztani, hogy megfeleljenek a '^[a-z][a-zA-Z0-9]*$' reguláris kifejezésnek.
Ez azért lényeges, mert ha esetleg több csapat is dolgozna, akkor ez segíti az együttműködést és a kód könnyebb megértését.

A Java-ban találhatóak szinkronizált osztályok, amelyeket azért hoztak létre, hogy thread safe-séget biztosítsanak, azonban cserébe ez erősen a teljesítmény rovására megy.
Ez javítandó a szinkronizált osztályokat (Stack) aszinkronra cseréltük (Deque).

Gyakori probléma volt továbbá switch-case szerkezetben a default ág implementásának elfelejtése, ez is javításra került.

A kód olvashatóságát javítandó több javítást is végeztünk:
- Felesleges zárójelek, castolások eltávoljtása
- Egymásba ágyazott if szerekezek merge-lése egy if szerkezetbe

Voltak olyan esetek, hogy több metódushívás feleslegesen bonyolította a kódot, mivel helyettesíthetők voltak egyetlen metódus hívással, amely
elvégezte a funkciójukat (toLowerCase + equals helyett equalsIgnoreCase). Ezt az egyszerűsítést is elvégeztük.

Egyes változónevek az úgynevezett Restricted Identifier kategóriába estek (pl. var), ezeket konvenció szerint érdemes kerülni, ezért átnevezésre kerültek.

Az üres body-jú override metódusokban kommentek feormájában feltüntettük a letisztultabb kód érdekében, hogy szándékosan vannak így használva.

Egyes erőforrások nem voltak megfelelően lezárva, exception esetén nem zárultak volna be. Ez probléma, ezt elkerülendő az erőforrás bezárásokat finally blokkokba tettük.

## Eredmények és tanulságok
A javítások eredményeképp véleményünk szerint egy sokkal jobb minőségű, kevesebb hibával rendelkező, olvashatóbb kód állt elő. A tanulság számunkra, hogy egy programírásakor rengeteg számunkra
apróságnak tűnő, de valójában jelentős részleten átsikolhat a figyelmünk, ezért rendkívül hasznos olyan statikus ellenőrző technológiákat használni, mint például a SonarCloud is. Ilyen programokkal lehetőségünk van felülvizsgálni a kódunk számtalan aspektusát és így minőségibb munkát kiadni a kezünk közül.