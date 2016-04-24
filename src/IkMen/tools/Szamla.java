package IkMen.tools;

import IkMen.exceptions.SzamlaException;
import IkMen.mysql.helpers.KilepesAdatok;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Számla készítő...
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
public class Szamla {

    private String TEMPLATE;
    private int PRICE;

    public Szamla(int price, String pathTemplate) throws SzamlaException{
        this.PRICE = price;
        try {
            

            String path = pathTemplate;

            List<String> lines = Files.readAllLines(Paths.get(path));

            for(String line : lines){
                if(line !=null) {
                    TEMPLATE += line;
                }
            }

        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
            throw new SzamlaException("[Szamla] Hiba a sablon olvasásánál!");
        }
    }

    public void create_szamla(KilepesAdatok data) {

        String file = TEMPLATE;

        System.out.println(data);


        file = file.replace("%NEVE%", data.nev);
        file = file.replace("%CIME%", data.cim);
        file = file.replace("%DATE%", data.kilepesido.split(" ")[0]);
        file = file.replace("%ORA%", String.format("%.3f",data.idotartam));
        file = file.replace("%ORA_AR%", Integer.toString(PRICE));
        file = file.replace("%NETTO_FT%", Integer.toString(data.fizetendo));
        file = file.replace("%AFA_SZ%", "27%");
        file = file.replace("%AFA_FT%", Integer.toString((int) (data.fizetendo * 0.27)));
        file = file.replace("%BRUTTO_FT%", Integer.toString((int) (data.fizetendo * 0.27) + data.fizetendo));
        file = file.replace(", ","\n");
        file = file.replace("null","");


        try {
            String fileName = "szamlak/"+data.nev + "_" + data.kilepesido + ".html";
            fileName = fileName.replace(" ","_");

            PrintWriter out = new PrintWriter(fileName);
            out.println(file);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new SzamlaException("[Szamla] Nem készült el a számla!");
        }

    }

}
