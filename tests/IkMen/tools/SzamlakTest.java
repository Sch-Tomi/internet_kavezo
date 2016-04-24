package IkMen.tools;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by tom on 2016.04.24..
 */
public class SzamlakTest {

    @Test
    public void testGetList() throws Exception {
        Szamlak szamla = new Szamlak();

        File folder = new File("szamlak");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> kulcsok = new ArrayList<>();
        if(listOfFiles != null){
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String name = listOfFile.getName();
                    name = name.replace("_2", " - 2");
                    name = name.replace("_", " ");

                    kulcsok.add(name);

                }
            }
        }
        assertTrue("Nem egyezik a beolvasott fájlok listája.",szamla.getList().toString().equals(kulcsok.toString()));

    }

    @Test
    public void testGetHtml() throws Exception {

        Szamlak szamla = new Szamlak();
        String file = szamla.getList().get(0);

        String test1 = szamla.getHtml(file);

        file = file.replace(" - 2","_2");
        file = file.replace(" ","_");

        String test2 = "";

        String fileName = "szamlak/"+file;
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        for(String line :lines){
            test2 += line;
        }

        assertTrue("Nem eggyeznek a szamla tartalmak!",test1.equals(test2));

    }
}