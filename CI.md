# Automatizáció és Build keretrendszer beüzemelése Mavennel + Github Actions CI beüzemelése

## Build keretrendszer beüzemelése


Az én feladatom volt elsősorban a build keretrendszer beüzemelése volt. Itt utánaolvastam a maven fájlszerkezet rendszerének, majd ezek alapján összegyűjtöttem a programban használt különböző dependency-ket és ezeket elhelyeztem a megfelelő struktúrába. A dependencyk között volt például a JavaFX.

## Github Actions CI beüzemelése

Ennél a feladatnál a gyakorlat amit tartottak nekünk nagyon hasznos volt, egy elindulási segítséget nyújtott a feladat elvégzéséhez. A gyakorlati segítségnek megfelelően készítettem el a build fájlt, majd kiegészítettem a java verzió megválasztásával, és a Maven package futtatásával. Ehhez adtam hozzé a sonarcloud statikus elemzést is.

## Tanulságok

A feladat megodlása során megismertem a Maven Projectet, így a feladat elvégzése során magabiztosan tudok bármilyen Java alkalmazás buildjét megírni.

Az automatizáció során pedig megismertem a Github workflow konvenciójat, és egy jó alapot adott más egyéb alkalmazások automatizálásának elvégzéséhez.
