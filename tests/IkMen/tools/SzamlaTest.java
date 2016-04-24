package IkMen.tools;

import IkMen.exceptions.SzamlaException;
import IkMen.mysql.helpers.KilepesAdatok;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

/**
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
 *
 */
public class SzamlaTest {

    @Test
    public void testSzamlaConstructor() throws Exception{
        boolean missingException = true;

        try{
            Szamla szamla = new Szamla(100,System.getProperty("user.dir")+"/test_res/template/template.txt");
        }catch (SzamlaException e){
            assertTrue("Exception message is diffrent", e.getMessage().equals("[Szamla] Hiba a sablon olvasásánál!"));
            missingException = false;
        }

        if(missingException){
            fail("Missing exception");
        }
    }

    @Test
    public void testCreate_szamla() throws Exception {

        Szamla szamla = new Szamla(100,System.getProperty("user.dir")+"/test_res/template/template_1.html");

        KilepesAdatok data = new KilepesAdatok("a","b","c","d","e",0,1,"f",2,"g",3,4);

        szamla.create_szamla(data);

        List<String> lines= Files.readAllLines(Paths.get("szamlak/"+data.nev + "_" + data.kilepesido + ".html"));

        assertTrue("A fájl tartalma nem eggyezik",lines.toString().equals("[acg4,000100327%03]"));

        Files.deleteIfExists(Paths.get("szamlak/"+data.nev + "_" + data.kilepesido + ".html"));

    }
}