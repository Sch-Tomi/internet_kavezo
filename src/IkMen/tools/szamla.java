package IkMen.tools;

import IkMen.exceptions.SzamlaException;
import IkMen.mysql.helpers.kilepesAdatok;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Számla készítő...
 *
 * Created by tom on 2016.03.12..
 */
public class szamla {

    private String TEMPLATE;
    private int PRICE;

    public szamla(int price) throws SzamlaException{
        this.PRICE = price;
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/IkMen/tools/template.html"));
            TEMPLATE = lines.toString();

        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
            throw new SzamlaException("[Szamla] Hiba a sablon olvasásánál!");
        }
    }

    public void create_szamla(kilepesAdatok data) {

        String file = TEMPLATE;

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
