package IkMen.tools;

import IkMen.exceptions.ConfigException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
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
 *
 **/
public class ConfigReader {



    private int PRICE;
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;

    public ConfigReader(String cfgPath) throws ConfigException{
        try {

            String path = cfgPath ;
            List<String> lines = Files.readAllLines(Paths.get(path));

            for(String line : lines){
                String key = line.split("=")[0].trim();
                String data = line.split("=")[1].trim();

                switch (key){
                    case "PRICE":
                        PRICE = Integer.parseInt(data);
                        break;
                    case "DB_URL":
                        DB_URL = data;
                        break;
                    case "DB_USER":
                        DB_USER = data;
                        break;
                    case "DB_PASS":
                        DB_PASS = data;
                        break;
                }

            }

        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new ConfigException("[CONFIG] Nem lehet olvasni a konfig fájlt!");
        }catch (Exception e){
            e.printStackTrace();
            throw new ConfigException("[CONFIG] Váraltlan hiba");
        }

    }

    public int getPRICE() {
        return PRICE;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getDB_USER() {
        return DB_USER;
    }

    public String getDB_PASS() {
        return DB_PASS;
    }
}
