package IkMen.tools;

import IkMen.exceptions.ConfigException;
import junit.framework.TestCase;


/**
 * Created by tom on 2016.04.19..
 */
public class ConfigReaderTest extends TestCase {

    public void testConfigReaderNormal(){

        ConfigReader cfgRead = new ConfigReader(System.getProperty("user.dir")+"/test_res/cfg/config_1.txt");

        assertTrue("Az ár nem eggyezik!",cfgRead.getPRICE() == 300);
        assertTrue("Az URL nem eggyezik!",cfgRead.getDB_URL().equals("jdbc:mysql://192.168.1.105:3306/ikmen"));
        assertTrue("A felhasználónév nem eggyezik!",cfgRead.getDB_USER().equals("test"));
        assertTrue("A jelszó nem eggyezik!",cfgRead.getDB_PASS().equals("qwe"));

    }

    public void testConfigReaderFileNotExists(){

        boolean missingException = true;

        try{
            ConfigReader cfgRead = new ConfigReader(System.getProperty("user.dir")+"/test_res/cfg/config.txt");
        }catch (ConfigException e){
            assertTrue("Exception message is diffrent", e.getMessage().equals("[CONFIG] Nem lehet olvasni a konfig fájlt!"));
            missingException = false;
        }

        if(missingException){
            fail("Missing exception");
        }

    }

    public void testConfigReaderEmpty(){

        ConfigReader cfgRead = new ConfigReader(System.getProperty("user.dir")+"/test_res/cfg/config_2.txt");

        assertTrue("Az ár nem nulla!",cfgRead.getPRICE() == 0);
        assertNull("Az URL nem null!",cfgRead.getDB_URL());
        assertNull("A felhasználónév nem null!",cfgRead.getDB_USER());
        assertNull("A jelszó nem null!",cfgRead.getDB_PASS());


    }


}