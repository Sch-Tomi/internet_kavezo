package mysql; /**
 * Created by tom on 2016.03.03..
 */

import java.sql.*;
import java.util.ArrayList;
import mysql.helpers.ugyfelArray;

public class db {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://db4free.net/";

    //  Database credentials
    private static final String USER = "cool";
    private static final String PASS = "asdqwe123";

    private Connection conn;
    private Statement stmt;

    public db() {
        conn = null;
        stmt = null;


        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }

    }

    // GÉPEK --->

    public ArrayList<Integer> getGepekID(){

        ArrayList<Integer> retArr = new ArrayList<Integer>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id FROM gepek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                retArr.add(rs.getInt("id"));
            }


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


        return retArr;
    }

    public void creatNewGep(int id, String text){

        try {
            String sql = "INSERT INTO gepek VALUES ("+Integer.toString(id)+", "+text+", 0)";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


    }

    public void setGepLeiras(int id, String text){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET leiras = "+ text +" WHERE id = "+Integer.toString(id);
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setGepAvaliable(int id){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 0 WHERE id = "+Integer.toString(id);
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setGepUnAvaliable(int id){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 1 WHERE id = "+Integer.toString(id);
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    // <--- Gépek

    // Ugyfelek --->

    public ArrayList<String> getUgyfelekList(){

        ArrayList<String> retArr = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT azon FROM ugyfelek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                retArr.add(rs.getString("azon"));
            }


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


        return retArr;
    }

    public ugyfelArray getUgyfel(String KerAzon){

        String nev;
        String azon;
        String cim;
        String szemSzam;
        String beido;

        int status;
        int egyenleg;
        int gepid;
        int pont;


        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM ugyfelek WHERE azon = "+KerAzon;
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            nev = rs.getString("nev");
            azon = rs.getString("azon");
            cim = rs.getString("cim");
            szemSzam = rs.getString("szemszam");
            beido = rs.getString("beido");

            status = rs.getInt("status");
            egyenleg = rs.getInt("egyenleg");
            gepid = rs.getInt("gepid");
            pont = rs.getInt("pont");

            return new ugyfelArray(nev, azon, cim,szemSzam, beido, status, egyenleg, gepid, pont);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

        return null;
    }

    public void setUgyfel(ugyfelArray ugyfel){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE ugyfelek SET " +
                    " nev = "+ugyfel.nev +
                    " cim = "+ugyfel.cim +
                    " szemSzam = "+ugyfel.szemSzam+
                    " WHERE id = "+ugyfel.azon;
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setEgyenleg(String azon, int money){

        try {
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT egyenleg FROM ugyfelek WHERE azon = "+ azon;
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            int current = rs.getInt("egyenleg");

            int newMoney = current + money;

            sql = "UPDATE ugyfelek SET " +
                    " egyenleg = "+ Integer.toString(newMoney)  +
                    " WHERE id = "+azon;
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }



    }


    // <--- Ugyfelek

}
