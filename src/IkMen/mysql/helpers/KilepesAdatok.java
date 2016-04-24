package IkMen.mysql.helpers;

/**
 *
 * Az ügyfelek adatai és a kilépésénél felmerülő, kilepesiidővel és fiezetendő össszeggel kibővítve.
 *
 * Internet Kávézó Menedzser - Internet kávézó gépbérlés nyílván tartása
 * Copyright (C) 2016  Schronk Tamás
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 */
public class KilepesAdatok extends UgyfelArray {

    public String kilepesido;
    public int fizetendo;
    public double idotartam;


    public KilepesAdatok(String nev, String azon, String cim, String szemSzam, String beido, int status, int egyenleg, String gepid, int pont, String kilepesido, int fizetendo, double idotartam){
        super(nev, azon, cim, szemSzam, beido, status, egyenleg, gepid, pont);

        this.kilepesido = kilepesido;
        this.fizetendo = fizetendo;
        this.idotartam = idotartam;
    }




}
