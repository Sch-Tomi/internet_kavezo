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

## Licence

 Internet Kávézó, ami egy internet kávézó nyílván tartását kezeli
 
 Copyright (C) 2016  Schronk Tamás

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 
 Ez a program szabad szoftver; terjeszthető illetve módosítható a 
 Free Software Foundation által kiadott GNU General Public License
 dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi 
 változata szerint.

 Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz, 
 de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA 
 VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve. 
 További részleteket a GNU General Public License tartalmaz.

 A felhasználónak a programmal együtt meg kell kapnia a GNU General 
 Public License egy példányát; ha mégsem kapta meg, akkor
 tekintse meg a <http://www.gnu.org/licenses/> oldalon.
