package IkMen.mysql; /**
 * Created by tom on 2016.03.03..
 */

import java.sql.*;
import java.util.ArrayList;
import IkMen.mysql.helpers.ugyfelArray;

public class model {

    // JDBC driver name and database URL
    private static final String DB_URL = "jdbc:mysql://192.168.1.100:3306/ikmen";

    //  Database credentials
    private static final String USER = "test";
    private static final String PASS = "qwe";

    private Connection conn;
    private Statement stmt;

    public model() {
        conn = null;
        stmt = null;

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");

        try {
            conn = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (conn != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }


    }

    // GÉPEK --->

    public ArrayList<String> getGepekIDs(){

        ArrayList<String> retArr = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id FROM gepek";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                retArr.add(rs.getString("id"));
            }


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


        return retArr;
    }

    public ArrayList<String> getGep(String azon){

        ArrayList<String> retArr = new ArrayList<String>();

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM gepek WHERE id = '"+azon+"'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.first()) {
                retArr.add(rs.getString("id"));
                retArr.add(rs.getString("leiras"));
                retArr.add(Integer.toString(rs.getInt("status")));
            }

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


        return retArr;


    }

    public void creatNewGep(String id, String text){

        try {
            String sql = "INSERT INTO gepek VALUES ('"+id+"', '"+text+"', 0)";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }


    }

    public void deleteGep(String azon){

        try {
            String sql = "DELETE FROM gepek WHERE id = '"+azon+"'";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setGepLeiras(String id, String text){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET leiras = '"+ text +"' WHERE id = '"+id+"'";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setGepAvaliable(String id){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 0 WHERE id = '"+id+"'";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public void setGepUnAvaliable(String id){

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE gepek SET status = 1 WHERE id = '"+id+"'";
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
