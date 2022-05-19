# JUnit 5 tesztek dokumentációja

A Junit tesztekkel az elsődleges cél az volt, hogy azon részleteket amiket a BDD tesztek nem képesek lefedni, azokat ellenőrizzék.

## ShuntingYardParserTest

A programban lehetőség van szövegként megadni számításokat, amelyet a program amikor végrehajtja az adott műveletet kiértékel. A kiértékelés a Shunting Yard algoritmussal van megvalósítva, ezek atesztek ennek az implementációját hivatott tesztelni.
A tesztek kitérnek olyan részekre, mint például több művelet összefűzése, hiányos bemenet, műveleti sorrend, zárójelezés helyes kiértékelése.

## String to Token
Az itt lévő tesztek a szövegből való parsolást és tokkenek-é alakítást vizsgálják.

## Commands test
A különböző poloskának adható parancsok tesztelése egy dummy poloskán. Egyenlőre erőforrások hiányában most csak a ciklusok ellenőrzése lett lefejlesztve.

# Összegzés
A tesztek megírásánál szempont volt, hogy a tesztek el legyenek szeparálva egymástól ne függjenek egy másik működés helyeségétől. A feladat remek lehetőségét kínált a Unit tesztek világába.
