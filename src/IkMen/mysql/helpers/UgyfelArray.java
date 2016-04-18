package IkMen.mysql.helpers;

/**
 *
 * Ügyfelek adatait egybe gyüjtő osztály.
 *
 * Created by tom
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
