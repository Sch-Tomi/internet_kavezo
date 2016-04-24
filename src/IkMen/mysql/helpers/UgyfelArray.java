package IkMen.mysql.helpers;

/**
 *
 * Ügyfelek adatait egybe gyüjtő osztály.
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
public class UgyfelArray {

    public String nev;
    public String azon;
    public String cim;
    public String szemSzam;
    public String beido;

    public int status;
    public int egyenleg;
    public String gepid;
    public int pont;

    public UgyfelArray(String nev, String azon, String cim, String szemSzam, String beido, int status, int egyenleg, String gepid, int pont){

        this.nev = nev;
        this.azon = azon;
        this.cim = cim;
        this.szemSzam = szemSzam;
        this.beido = beido;
        this.status = status;
        this.egyenleg = egyenleg;
        this.gepid = gepid;
        this.pont = pont;

    }


}
