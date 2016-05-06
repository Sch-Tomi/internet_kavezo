package IkMen.mysql;

import IkMen.exceptions.DataBaseException;
import IkMen.mysql.helpers.KilepesAdatok;
import IkMen.mysql.helpers.UgyfelArray;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 *
 * Testing Model class
 *
 * Created by tom on 2016.04.30..
 */
public class ModelTest {

    static Model db;

    @BeforeClass
    public static void beforeClass() {

        truncate();

        db = new Model(100,"jdbc:mysql://db4free.net/myk5wp_prog_test","myk5wp_prog_test","myk5wp_prog_test");



    }

    public static void truncate(){

        Connection conn;
        Statement stmt;


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataBaseException("[DB - TRUNCATE] Hiányzó MySql Driver");
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net/myk5wp_prog_test","myk5wp_prog_test","myk5wp_prog_test");

        } catch (SQLException e) {
            throw new DataBaseException("[DB - TRUNCATE] Nem sikerült csatlakozni");
        }

        if (conn == null) {
            throw new DataBaseException("[DB - TRUNCATE] Nem sikerült csatlakozni");
        }

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "TRUNCATE TABLE  befizetesek";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "TRUNCATE TABLE  gepek";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "TRUNCATE TABLE  hasznalat";
            stmt.executeUpdate(sql);

            stmt = conn.createStatement();
            sql = "TRUNCATE TABLE  ugyfelek";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL - TRUNCATE] NEM SIKERÜLT TÖRÖLNI!!! .");
        }



    }

    @Test
    public void testGepek() throws Exception {

        String gepAzon = "test1";
        String gepLeiras = "testText1";
        String gepUjLeiras = "test222";


        // gép hozzáadása
        try {
            db.creatNewGep(gepAzon,gepLeiras);
        }catch (DataBaseException e){
            fail(e.getMessage());
        }
        // gép ellenörzése getGep == getUresGepekIDs
        try {
            ArrayList<String> res1 = db.getGep(gepAzon);
            ArrayList<String> res2 = db.getUresGepekIDs();
            ArrayList<String> res3 = db.getGepekIDs();

            assertTrue("Nem eggyezik getGep == getUresGepekIDs",res1.get(0).equals(res2.get(0)));
            assertTrue("Nem eggyezik getGep == getGepekIDs",res1.get(0).equals(res3.get(0)));

        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        // leiras friss
        try{

            db.setGepLeiras(gepAzon,gepUjLeiras);

            assertTrue("Nem frissült a leírás!",db.getGep(gepAzon).get(1).equals(gepUjLeiras));

        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        // státusz friss
        try {

            // foglaltra állítás
            db.setGepUnAvaliable(gepAzon);
            assertTrue("Nem lett foglalt!",db.getGep(gepAzon).get(2).equals("1"));

            // szabadra
            db.setGepAvaliable(gepAzon);
            assertTrue("Nem lett szabad!",db.getGep(gepAzon).get(2).equals("0"));


        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        // geptörles
        try {
            db.deleteGep(gepAzon);
            assertTrue("Nem törölte a gépet",db.getGepekIDs().size()==0);
        }catch (DataBaseException e){
            fail(e.getMessage());
        }


    }

    /// ÜGYFÉL!!!


    @Test
    public void testUgyfel() throws Exception {

        String nev = "User1";
        String azon = "US1";
        String cim = "CIM1";
        String szemSzam = "123";
        String beido = "";

        int status = 0;
        int egyenleg = 0;
        String gepid = "";
        int pont = 0;

        UgyfelArray defUgyfel = new UgyfelArray(nev,azon,cim,szemSzam,beido,status,egyenleg,gepid,pont);

        String modCim = "CIM2";

        UgyfelArray modUgyfel = new UgyfelArray(nev,azon,modCim,szemSzam,beido,status,egyenleg,gepid,pont);


        // ügyfél létrehozása
        try {
            db.createUgyfel(defUgyfel);
        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        // ügyfél lekérdezése
        try {
            assertTrue("Nem eggyezik getUgyfel == getUgyfelList[0]",db.getUgyfelekList().get(0).equals(db.getUgyfel(azon).azon));
        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        //Ügyfél módosítás
        try {
            db.setUgyfel(modUgyfel);
            assertTrue("nem Változott az ügyfél címe",db.getUgyfel(azon).cim.equals(modCim));

        }catch (DataBaseException e){
            fail(e.getMessage());
        }

        // Ügyfél Egyenleg
        try {

            db.setEgyenleg(azon,100);
            assertTrue("Nem adta hozzá az egyenleget",db.getUgyfel(azon).egyenleg == 100);

            db.setEgyenleg(azon,-100);
            assertTrue("Nem vette le!",db.getUgyfel(azon).egyenleg == 0);

            // LOG
            db.setEgyenleg(azon,100);
            db.setEgyenleg(azon,-100);
            assertTrue("Nincs LOG!",db.getBefizetesekList().size()==2);

        }catch (DataBaseException e){
            fail(e.getMessage());
        }


        // Ügyfél Be ki
        try {

            String gepAzon = "test_ugyfel";
            String gepLeiras = "testText1";
            // gép létrehozása
            db.creatNewGep(gepAzon,gepLeiras);

            // Mani
            db.setEgyenleg(azon,100);

            // BE
            db.ugyfelBe(azon,gepAzon);
            assertTrue("Nem jelentkezett be",db.getUgyfel(azon).status == 1);

            // KI
            KilepesAdatok data = db.getUgyfelKiAdat(azon);
            db.ugyfelKi(data);
            assertTrue("Nem jelentkezett ki",db.getUgyfel(azon).status == 0);

            // LOG
            assertTrue("Nincs LOG a használatról!",db.getHasznalatList().get(0).get(0).equals(azon));

            // gép törlése
            db.deleteGep(gepAzon);

        }catch (DataBaseException e){
            fail(e.getMessage());
        }

    }



    @Test
    public void testCalcReduce() throws Exception {

        assertTrue("Rossz engedmény! (1)",db.calcReduce(1000,0)==0);

        assertTrue("Rossz engedmény! (2)",db.calcReduce(1000,1499)==90);
        assertTrue("Rossz engedmény! (3)",db.calcReduce(1000,1500)==100);
        assertTrue("Rossz engedmény! (4)",db.calcReduce(1000,1501)==100);

        assertTrue("Rossz engedmény! (5)",db.calcReduce(1000,1500000)==100);


    }

    @Test
    public void testCalcPont() throws Exception {

        //16 elött
        assertTrue("Rossz pont számítás (1)",db.calcPont("2016-05-01 13:25:02.595","2016-05-01 14:25:02.595")==2);
        assertTrue("Rossz pont számítás (2)",db.calcPont("2016-05-01 12:25:02.595","2016-05-01 14:25:02.595")==4);
        assertTrue("Rossz pont számítás (2)",db.calcPont("2016-05-01 12:25:02.595","2016-05-01 14:25:02.596")==4);
        // 21 után
        assertTrue("Rossz pont számítás (3)",db.calcPont("2016-05-01 21:05:02.595","2016-05-01 22:05:02.595")==2);

        // 16-21 között
        assertTrue("Rossz pont számítás (4)",db.calcPont("2016-05-01 16:25:02.595","2016-05-01 17:25:02.595")==1);

        // Átlógás a 1-2 pont között
        assertTrue("Rossz pont számítás (5)",db.calcPont("2016-05-01 14:50:00.000","2016-05-01 16:50:00.000")==3);

    }
}