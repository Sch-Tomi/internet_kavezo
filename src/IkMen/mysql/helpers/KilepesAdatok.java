package IkMen.mysql.helpers;

/**
 *
 * Az ügyfelek adatai és a kilépésénél felmerülő, kilepesiidővel és fiezetendő össszeggel kibővítve.
 *
 * Created by tom on 2016.03.11..
 */
public class KilepesAdatok extends UgyfelArray {

    public String kilepesido;
    public int fizetendo;
    public double idotartam;


    public KilepesAdatok(String nev, String azon, String cim, String szemSzam, String beido, int status, int egyenleg, String gepid, int pont, String kilepesido, int fizetendo, double idotartam){
        super(nev, azon, cim, szemSzam, beido, status, egyenleg, gepid, pont);

        this.kilepesido = kilepesido;
        this.fizetendo = fizetendo;
        this.idotartam = idotartam;
    }




}
