# Internet Kávézó
Progtech. homework/beadandó

## Feladat:

Készítsünk programot, amely egy internet kávézó nyilván
tartását tudja kezelni, az alábbi 
funkciókkal:  
* A programban megtekinthetőek a számítógépek adatai 
(azonosító, hardver leírás, 
operációs rendszer).  
* Felvehetünk új ügyfeleket, akiknek megadjuk a nev
ét, személyi igazolvány számát, 
címét, valamint felhasználónevét és jelszavát. Az adato
kat a későbbiekben 
módosíthatjuk is (kivéve a felhasználónevet).  
* Az ügyfeleket is listázhatjuk a programban (azonosít
ó, cím, személyi igazolvány 
szám). Az ügyfél kiválasztásával beléptethetjük, valami
nt kiléptethetjük a kávézóból. 
Beléptetéskor kiválasztjuk a számítógépet, amelyet az ü
gyfél elfoglal (természetesen 
csak szabad gépet lehet megadni és egy ügyfél csak egy sz
ámítógépet használhat 
egyszerre). Az időpontok automatikusan rögzítésre kerül
nek, és ezek alapján 
számolódik az ügyfél számlája amelyet az ügyfeleknél 
tekinthetünk meg a számla 
következő módon áll össze: az ügyfél óránként bizonyo
s összegű alapdíjat fizet, majd 
a végösszegből levonásra kerül bizonyos mennyiségű kedve
zmény. 
* A kedvezmény mértékének meghatározása: Az ügyfelek 
minden használati óra után 
2 pontot kapnak 16:00-óra előtt és 21:00 után, a közt
es idő intervallumban 1 pontot, a 
nem egész órakkor kezdődő intervallum esetén a kevesebb
 pontot kell elszámolni 
(pl.: 14:50-16:50-> 3 pont: 14:50-15:50 2 pont + 15
:50-16:50  -> 1 pont), az ügyfél 
minden 150 pont után 1% állandó kedvezményt kap, de
 legfeljebb 10%-ot.  
* Szintén az ügyfél kiválasztásával végezhetünk befize
tést. A befizetett összegeket le 
kell számolnunk a számlából. Olyan ügyfél, akinek hián
yzó befizetései vannak nem 
léptethető ki. 

Az adatbázis az alábbi adatokat tárolja (ezek még nem
 feltétlenül a fizikai adattáblák):  
* ügyfelek (azonosító, jelszó, cím, személyi igazolván
y szám);  
* számítógépek (azonosító, hardver leírás, operációs rend
szer);  
* használat (ügyfél azonosító, számítógép azonosító, b
ejelentkezés időpontja, 
kijelentkezés időpontja);  
* befizetések (ügyfél azonosító, összeg).  
