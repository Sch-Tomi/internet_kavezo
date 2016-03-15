package IkMen.tools;

import IkMen.exceptions.ConfigException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by tom on 2016.03.14..
 */
public class configReader {



    private int PRICE;
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;

    public configReader() throws ConfigException{
        try {

            //TODO filepath...

            String path = System.getProperty("user.dir")+"/config.txt";
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
