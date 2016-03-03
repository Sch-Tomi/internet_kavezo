package mysql.helpers;

/**
 * Created by tom on 2016.03.03..
 */
public class ugyfelArray {

    public String nev;
    public String azon;
    public String cim;
    public String szemSzam;
    public String beido;

    public int status;
    public int egyenleg;
    public int gepid;
    public int pont;

    public ugyfelArray(String nev, String azon, String cim, String szemSzam, String beido, int status, int egyenleg, int gepid, int pont){

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
