# Bdd tesztek dokumentáció

Mint minden másnál itt is a munka a felfedezés fázisával kezdődött. Először az elérhető eszközök, könyvtárak felfedezése történt meg. A könyvtárak közül 2 darab tűnt szimpatikusnak a JBehave és a Cucumber. Végül a Cucumber-re esett a választás főleg a részletesebb support és elérhető dokumentáció miatt.
Mostmár, hogy megvoltak az eszközök. A program átnézése volt a következő és arra a következtetésre jutottam, hogy értelmes formában a következő 2 működést lehet a BDD tesztekkel ellenőrizni.

1. A Poloska egyszerű mozgása, megadott körülmények között
2. A programon belül definiált változók semi-automatic elnevezése elnevezése, különböző helyzetekben.

## A Poloska egyszerű mozgása:
Itt erősen szerepet kapott a Cucumber által lehetővé tett paraméterek, melyek segítettek a Szenáriókat minél értelmezhetőbben megfogalmazni. Például a poloska helyzetét megtudjuk a következőképpen fogalmazni.

> Given: a Poloska at x:*0* y:*0* facing *right*

## A változók elnevezése:
A programon belül definiálhatók változók, de ezek sokszor azért, hogy ne akadjanak össze, automatikusan valamilyen elnevezési konvenció alapján kapják a nevüket. Jó példa a ciklusok változói, melyek az egymásba beágyazott ciklusoknál fontos, hogy egyedi helyesen megszámozott változókat kapjanak. Ezen felül a függvények is rendelkeznek egy speciális elnevezési konvencióval szintúgy az összeakadás elkerülése végett. Ezek mind tesztelve vannak.

A program többi részére nehézkesen lehetett volna ezt a fajta tesztelési konvenciót ráhúzni ezért JUnit tesztekkel kerültek kiegészítésre.

A feladat lezárása volt megvalósítani, hogy a Maven build-el együtt lefutathatóak legyenek.

## Tanulságok

A feladat megoldása során, megtanulatam milyen fajta működéseket lehet BDD tesztekkel ellenőrizni, valamint különböző konvenciókat melyek segítségével könnyen értelmezhető szenárió leírásokat készíthetünk. Valamint lehetőségem volt a tesztek futtatásával validálni a működést. ezek után már magabiztosan tudom használni a Cucumber könyvtárat BDD tesztek megvalósítására.
