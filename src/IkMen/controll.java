package IkMen;

/**
 * Created by tom on 2016.03.04..
 */


import IkMen.gui.view;
import IkMen.mysql.model;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class controll {

    private view gui;
    private model db;

    private final Action tabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Első tab beállításnál NullPointer Exception kivédése
            if(gui != null && db != null) {
                switch (Integer.parseInt(e.getActionCommand())) {
                    case 0: //gepek
                        gui.updateGepList(db.getGepekIDs());
                        break;
                    case 1: // ugyfelek:

                        break;
                }
            }
        }
    };

    private final Action comboAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

               gui.updateLeiras(db.getGep(e.getActionCommand()));

            }

    };


    private final Action controllAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (Integer.parseInt(e.getActionCommand())) {
                case 1: //"gepek_ujgep":
                    ArrayList<String> tmp = gui.makeNewMachine();
                    db.creatNewGep(tmp.get(0),tmp.get(1));
                    updateGui();
                    break;
                case 2: // "gepek_modositas":
                    db.setGepLeiras(gui.getCurrentItem(), gui.getLeiras());
                    System.out.println("Leírás frissült!");
                    break;
                case 3: //"ugyfelek_ujUgyfel":

                    break;
                case 4: // "ugyfel_befizetes":

                    break;
                case 5: // "ugyfel_be":

                    break;
                case 6: //"ugyfel_ki":

                    break;
                case 7:
                    db.deleteGep(gui.getCurrentItem());
                    System.out.println("Gép Törölve");
                    updateGui();
                    break;
                default:
                    break;
            }


        }
    };

    public controll(){

        db = new model();

        ArrayList<Integer> guiButtons = new ArrayList<Integer>(){{
            add(1); //gepek_ujgep
            add(2); //"gepek_modositas"
            add(3); //"ugyfelek_ujUgyfel"
            add(4); //"ugyfel_befizetes"
            add(5); //"ugyfel_be"
            add(6); //"ugyfel_ki"
            add(7); // gepek_torles
        }};


        gui = new view(controllAction, guiButtons, tabAction, comboAction);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);

        // TODO: Indulás utáni adat feltöltés...
        updateGui();


    }

    private void updateGui(){

        gui.updateGepList(db.getGepekIDs());

    }


}

