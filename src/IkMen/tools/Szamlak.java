package IkMen.tools;

import IkMen.exceptions.SzamlakException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * Szamlak begyüjtési, vissza adása...
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
public class Szamlak {

    private Map<String, String> dict;
    private ArrayList<String> kulcsok;

    public Szamlak() throws SzamlakException{
        try {
            dict = new HashMap<>();
            kulcsok = new ArrayList<>();
        }catch (Exception e){
            throw new SzamlakException("[Szamlak] Inicializáslási Hiba!");
        }

    }

    public ArrayList<String> getList() throws SzamlakException{
        refresh();
        return kulcsok;
    }

    public String getHtml(String file) throws SzamlakException{

        String retSt= "";
        try {
            String fileName = "szamlak/"+dict.get(file);
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            for(String line :lines){
                retSt += line;
            }

        }catch (IOException e){
            throw new SzamlakException("[Szamlak] Nem lehet beolvasni a fájlt!");
        }

        return retSt;
    }

    private void refresh() throws SzamlakException{

        dict.clear();
        kulcsok.clear();


        try {
            File folder = new File("szamlak");
            File[] listOfFiles = folder.listFiles();

            if(listOfFiles != null){
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        String name = listOfFile.getName();
                        name = name.replace("_2", " - 2");
                        name = name.replace("_", " ");

                        dict.put(name, listOfFile.getName());
                        kulcsok.add(name);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new SzamlakException("[Szamlak] Hiba a szamlak begyüjtésénél");
        }



    }
}
