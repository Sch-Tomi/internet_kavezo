import IkMen.controll;

import javax.swing.*;

/**
 * Created by tom on 2016.03.03..
 */
public class main {




    public static void main(String[] args){
        try {
            // Set System L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        controll wind = new controll();

    }

}
