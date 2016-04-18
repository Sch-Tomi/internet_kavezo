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
 * Created by tom on 2016.03.14..
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
