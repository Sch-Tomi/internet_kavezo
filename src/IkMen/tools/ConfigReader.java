package IkMen.tools;

import IkMen.exceptions.ConfigException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.json.*;


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

            String path = cfgPath;
            String cfgLine = "";
            List<String> lines = Files.readAllLines(Paths.get(path));

            if (lines.size() == 0) {
                throw new ConfigException("[CONFIG] Üres fájl!");
            }

            for (String line : lines) {
                cfgLine += line;
            }


            JSONObject obj = new JSONObject(cfgLine);

            PRICE = obj.getInt("PRICE");
            DB_URL = obj.getString("DB_URL");
            DB_USER = obj.getString("DB_USER");
            DB_PASS = obj.getString("DB_PASS");


        }catch (ConfigException cfge){
            cfge.printStackTrace();
            throw cfge;
        }catch (IOException ioe){
            ioe.printStackTrace();
            throw new ConfigException("[CONFIG] Nem lehet olvasni a konfig fájlt!");
        }catch (JSONException jse){
            jse.printStackTrace();
            throw new ConfigException("[CONFIG] JSON olvasása nem sikerült!");
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
