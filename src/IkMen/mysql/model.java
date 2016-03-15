package IkMen.mysql;

/**
 * A program MySql kapcsolatát biztosító osztály.
 * <p>
 * Created by tom on 2016.03.03..
 */

import IkMen.exceptions.DataBaseException;
import IkMen.mysql.helpers.DateUtils;
import IkMen.mysql.helpers.kilepesAdatok;
import IkMen.mysql.helpers.ugyfelArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class model {

    // JDBC driver name and database URL
    private String DB_URL;

    //  Database credentials
    private String USER;
    private String PASS;

    private Connection conn;
    private Statement stmt;

    private int PRICE;

    public model(int price, String DB_URL, String USER, String PASS) throws DataBaseException {

        PRICE = price;
        this.DB_URL= DB_URL;
        this.USER = USER;
        this.PASS = PASS;

        conn = null;
        stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataBaseException("[DB] Hiányzó MySql Driver");
        }

        try {
            conn = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            throw new DataBaseException("[DB] Nem sikerült csatlakozni");
        }

        if (conn == null) {
            throw new DataBaseException("[DB] Nem sikerült csatlakozni");
        }

    }

    // GÉPEK --->

    public ArrayList<String> getGepekIDs() throws DataBaseException {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id FROM gepek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retArr.add(rs.getString("id"));
            }


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni a gépek listáját.");

        }


        return retArr;
    }

    public ArrayList<String> getUresGepekIDs() throws DataBaseException {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id FROM gepek WHERE status = 0";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retArr.add(rs.getString("id"));
            }


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni a szabad gépek listáját.");
        }


        return retArr;
    }

    public ArrayList<String> getGep(String azon) throws DataBaseException {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM gepek WHERE id = '" + azon + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.first()) {
                retArr.add(rs.getString("id"));
                retArr.add(rs.getString("leiras"));
                retArr.add(Integer.toString(rs.getInt("status")));
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni a gépet.");
        }


        return retArr;


    }

    public void creatNewGep(String id, String text) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO gepek VALUES ('" + id + "', '" + text + "', 0)";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült létrehozni az új gépet.");
        }


    }

    public void deleteGep(String azon) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql ="SELECT * FROM gepek WHERE id = '"+azon+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            if(rs.getInt("status") == 1){
                throw new DataBaseException("[SQL] Nem lehet törölni a gépet mert épp használatban van!");
            }

            sql = "DELETE FROM gepek WHERE id = '" + azon + "'";
            stmt.executeUpdate(sql);


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült törölni a gépet .");
        }

    }

    public void setGepLeiras(String id, String text) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET leiras = '" + text + "' WHERE id = '" + id + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült frissíteni a gép leírását .");
        }

    }

    public void setGepAvaliable(String id) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 0 WHERE id = '" + id + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült felszabadítani a gépet.");
        }

    }

    public void setGepUnAvaliable(String id) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 1 WHERE id = '" + id + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült lefoglalni a gépet.");
        }

    }

    // <--- Gépek

    // Ugyfelek --->

    public void deleteUgyfel(String azon) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql = "DELETE FROM ugyfelek WHERE azon = '" + azon + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült törölni az ügyfelet.");
        }

    }


    public ArrayList<String> getUgyfelekList() throws DataBaseException {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT azon FROM ugyfelek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retArr.add(rs.getString("azon"));
            }


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni az ügyfél listát.");
        }


        return retArr;
    }

    public ugyfelArray getUgyfel(String KerAzon) throws DataBaseException {

        String nev;
        String azon;
        String cim;
        String szemSzam;
        String beido;

        int status;
        int egyenleg;
        String gepid;
        int pont;


        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ugyfelek WHERE azon = '" + KerAzon + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            nev = rs.getString("nev");
            azon = rs.getString("azon");
            cim = rs.getString("cim");
            szemSzam = rs.getString("szemszam");
            beido = rs.getString("beido");

            status = rs.getInt("status");
            egyenleg = rs.getInt("egyenleg");
            gepid = rs.getString("gepid");
            pont = rs.getInt("pont");

            return new ugyfelArray(nev, azon, cim, szemSzam, beido, status, egyenleg, gepid, pont);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni az ügyfél adatait.");
        }


    }

    public void setUgyfel(ugyfelArray ugyfel) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE ugyfelek SET " +
                    " nev = '" + ugyfel.nev + "', " +
                    " cim = '" + ugyfel.cim + "', " +
                    " szemSzam = '" + ugyfel.szemSzam + "'" +
                    " WHERE azon = '" + ugyfel.azon + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült frissíteni az ügyfél adatait.");
        }

    }

    public void createUgyfel(ugyfelArray ugyfel) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO ugyfelek VALUES (" +
                    "'" + ugyfel.azon + "', " +
                    "'" + ugyfel.cim + "', " +
                    "'" + ugyfel.szemSzam + "', " +
                    "'" + ugyfel.nev + "', " +
                    "'" + ugyfel.beido + "', " +
                    "'" + ugyfel.egyenleg + "', " +
                    "'" + ugyfel.status + "', " +
                    "'" + ugyfel.gepid + "', " +
                    "'" + ugyfel.pont + "')";

            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült létrehozni az ügyfél ptofilját.");
        }

    }

    public void setEgyenleg(String azon, int money) throws DataBaseException {

        try {
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT egyenleg FROM ugyfelek WHERE azon = '" + azon + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            int current = rs.getInt("egyenleg");

            int newMoney = current + money;

            sql = "UPDATE ugyfelek SET " +
                    " egyenleg = '" + Integer.toString(newMoney) + "'" +
                    " WHERE azon = '" + azon + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült az ügyfél egyenlegének módosítása.");
        }


        // Ha befizetés történt és nem használat utána levonás akkor logolunk
        if (money > 0) {
            setEgyenlegLog(azon, money);
        }


    }

    public void setEgyenlegLog(String azon, int money) throws DataBaseException {

        try {
            stmt = conn.createStatement();

            String sql = "INSERT INTO befizetesek VALUES( " +
                    "'" + azon + "'," +
                    "'" + Integer.toString(money) + "')";

            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült a befizetésről logot készíteni.");
        }

    }

    public void ugyfelBe(String ugyfel, String gepid) throws DataBaseException {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE ugyfelek SET " +
                    " status = '1'," +
                    " gepid = '" + gepid + "'," +
                    " beido = '" + new Timestamp(new java.util.Date().getTime()).toString() + "'" +
                    " WHERE azon = '" + ugyfel + "'";
            stmt.executeUpdate(sql);

            setGepUnAvaliable(gepid);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült beléptetni az ügyfélt.");
        }
    }

    public void ugyfelKi(kilepesAdatok data) throws DataBaseException {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE ugyfelek SET " +
                    " status = '0'," +
                    " gepid = '-1'," +
                    " beido = '-'," +
                    " pont = '" + Integer.toString(data.pont) + "'" +
                    " WHERE azon = '" + data.azon + "'";
            stmt.executeUpdate(sql);

            setGepAvaliable(data.gepid);
            setUgyfelKiLog(data.azon, data.gepid, data.beido, new Timestamp(new java.util.Date().getTime()).toString());

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült kiléptetni az ügyfélt.");
        }
    }

    public void setUgyfelKiLog(String azon, String gepid, String be, String ki) {

        try {
            stmt = conn.createStatement();

            String sql = "INSERT INTO hasznalat VALUES('" + azon + "','" + gepid + "','" + be + "','" + ki + "' )";

            System.out.println(sql);
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült használati logot készíteni.");
        }


    }

    public kilepesAdatok getUgyfelKiAdat(String KerAzon) throws DataBaseException {
        String nev;
        String azon;
        String cim;
        String szemSzam;
        String beido;

        int status;
        int egyenleg;
        String gepid;
        int pont;


        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ugyfelek WHERE azon = '" + KerAzon + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            nev = rs.getString("nev");
            azon = rs.getString("azon");
            cim = rs.getString("cim");
            szemSzam = rs.getString("szemszam");
            beido = rs.getString("beido");

            status = rs.getInt("status");
            egyenleg = rs.getInt("egyenleg");
            gepid = rs.getString("gepid");
            pont = rs.getInt("pont");
            pont += calcPont(beido, new Timestamp(new java.util.Date().getTime()).toString());

            // Calc
            long now = System.currentTimeMillis() / 1000;
            Timestamp timestamp = Timestamp.valueOf(beido);
            long beidoSec = timestamp.getTime() / 1000;

            double hours = (now - beidoSec) / 3600.0;

            int fizetendo = (int) (hours * PRICE);
            fizetendo = calcReduce(fizetendo,pont);

            return new kilepesAdatok(nev, azon, cim, szemSzam, beido, status, egyenleg, gepid, pont, new Timestamp(new java.util.Date().getTime()).toString(), fizetendo, hours);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni az ügyfél kilépési adatait.");
        }

    }

    public int calcReduce(int money, int pont){

        double reduce = ((pont/150)*0.01 > 0.015) ? 0.015 : (pont/150)*0.01;

        return (int)(money*reduce);
    }

    public int calcPont(String be, String ki) {

        int pont = 0;

        Timestamp timestamp = Timestamp.valueOf(be);
        java.util.Date beIdo = new Date(timestamp.getTime());

        timestamp = Timestamp.valueOf(ki);
        java.util.Date kiIdo = new Date(timestamp.getTime());

        java.util.Date calcTime = beIdo;

        Calendar cal = Calendar.getInstance();

        while (calcTime.before(kiIdo)) {

            java.util.Date begin = calcTime;
            java.util.Date end = DateUtils.addHours(calcTime, 1);

            cal.setTime(begin);
            int hourBE = cal.get(Calendar.HOUR_OF_DAY);

            cal.setTime(end);
            int hourKI = cal.get(Calendar.HOUR_OF_DAY);

            if ((hourBE >= 16 && hourBE < 21) || (hourKI >= 16 && hourKI < 21)) {
                pont++;
            } else {
                pont += 2;
            }


            calcTime = DateUtils.addHours(calcTime, 1);
        }


        return pont;
    }


    // <--- Ugyfelek

    // Befizetesek

    public ArrayList<String> getBefizetesekList() {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM befizetesek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retArr.add(rs.getString("azon") + " : " + rs.getString("osszeg"));
            }


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni a befizetesek listát.");
        }


        return retArr;


    }

    //Használat
    public ArrayList<String> getHasznalatList() {

        ArrayList<String> retArr = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM hasznalat";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                retArr.add(rs.getString("azon") + " : " + rs.getString("gepid") + "      " + rs.getString("be") + " - " + rs.getString("ki"));
            }


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            throw new DataBaseException("[SQL] Nem sikerült megkapni a használat listát.");
        }


        return retArr;


    }


}
