package IkMen;

/**
 *
 * A kezelő osztály, összeköti a GUI-t és az adadtbázis elérést.
 *
 * Created by tom on 2016.03.04..
 */


import IkMen.exceptions.DataBaseException;
import IkMen.exceptions.GuiException;
import IkMen.exceptions.SzamlaException;
import IkMen.gui.view;
import IkMen.mysql.helpers.kilepesAdatok;
import IkMen.mysql.helpers.ugyfelArray;
import IkMen.mysql.model;
import IkMen.tools.szamla;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class controll {

    private view gui;
    private model db;
    private szamla szamla;

    private final Action tabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Első tab beállításnál NullPointer Exception kivédése
            if(gui != null && db != null) {
                try {
                    switch (Integer.parseInt(e.getActionCommand())) {
                        case 0: //gepek
                            gui.updateGepList(db.getGepekIDs());
                            break;
                        case 1: // ugyfelek:
                            gui.updateUgyfelList(db.getUgyfelekList());
                            break;
                        case 2: // befizetesek
                            gui.updateBefizetesekList(db.getBefizetesekList());
                            break;
                        case 3:
                            gui.updateHasznalatList(db.getHasznalatList());
                            break;
                    }
                }catch (DataBaseException dbe){
                    showExceptionError(dbe);
                }

            }
        }
    };

    private final Action comboAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                gui.updateLeiras(db.getGep(e.getActionCommand()));
            }catch (DataBaseException dbe){
                showExceptionError(dbe);
            }


            }

    };


    private final Action controllAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                switch (Integer.parseInt(e.getActionCommand())) {
                    case 1: //"gepek_ujgep":
                        ArrayList<String> tmp = gui.makeNewMachine();
                        db.creatNewGep(tmp.get(0),tmp.get(1));
                        updateGui();
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "A gépet felvettük!");
                        break;

                    case 2: // "gepek_modositas":
                        db.setGepLeiras(gui.getCurrentGepItem(), gui.getLeiras());
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "A gép leírása frissült!");
                        break;

                    case 3: //"ugyfelek_ujUgyfel":
                        db.createUgyfel(gui.newUser());
                        updateGui();
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Sikerült felvenni az új ügyfél adatait!");
                        break;

                    case 4: // "ugyfel_befizetes":
                        db.setEgyenleg(gui.getCurrentUgyfel(), gui.befizetes());
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél számlájához hozzá adtuk az összeget!");
                        break;

                    case 5: // "ugyfel_be":
                        String uresgep = gui.UgyfelBe(db.getUresGepekIDs());
                        db.ugyfelBe(gui.getCurrentUgyfel(), uresgep);
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél sikerült beléptetni!");
                        break;

                    case 6: //"ugyfel_ki":
                        kilepesAdatok data = db.getUgyfelKiAdat(gui.getCurrentUgyfel());

                        if(data.fizetendo <= data.egyenleg){
                            db.setEgyenleg(data.azon,-data.fizetendo);
                            szamla.create_szamla(data);
                            db.ugyfelKi(data);

                            gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél sikeresen kilépett a számla a szokott helyen található!");
                        }else{
                            gui.Notification(JOptionPane.WARNING_MESSAGE, "Kiléptetési Hiba!", "A felhasználó számláján nicns elég pénz.");
                        }

                        break;

                    case 7:
                        db.deleteGep(gui.getCurrentGepItem());
                        updateGui();
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Gép törölve!");
                        break;

                    case 8:
                        ugyfelArray delData =db.getUgyfel(gui.getCurrentUgyfel());

                        if(delData.status == 0){
                            db.deleteUgyfel(gui.getCurrentUgyfel());
                            String msg = String.format("A felhasználót siekresen törölve!\nA törlés elött fent maradó összeg:%s",Integer.toString(delData.egyenleg));
                            gui.Notification(JOptionPane.INFORMATION_MESSAGE,"Sikeres Művelet",msg);
                        }else{
                            gui.Notification(JOptionPane.INFORMATION_MESSAGE,"Sikertelen Művelet","A felhasználó addig nem törölhető amíg bevan jelentkezve!");
                        }

                        updateGui();
                        break;

                    case 9:
                        ugyfelArray oldData = db.getUgyfel(gui.getCurrentUgyfel());
                        ugyfelArray currentData = gui.getCurrentUgyfelData();

                        oldData.nev = currentData.nev;
                        oldData.cim = currentData.cim;
                        oldData.szemSzam = currentData.szemSzam;

                        db.setUgyfel(oldData);

                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "A felhasználó adatait frissítettük.");
                        break;

                    default:
                        updateGui();
                        break;
                }
            }catch (DataBaseException dbe){
                showExceptionError(dbe);
            }catch (SzamlaException sze){
                showExceptionError(sze);
            }catch (GuiException ge){
                showExceptionError(ge);
            }

            updateGui();

        }
    };

    public Action ugyfelekListaAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                gui.updateUgyfelDatas(db.getUgyfel(e.getActionCommand()));
            }catch (DataBaseException dbe){
                showExceptionError(dbe);
            }

        }
    };

    public controll(){

        int price = 0;

        ArrayList<Exception> errors = new ArrayList<Exception>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("src/price.txt"));
            price = Integer.parseInt(lines.get(0));

            db = new model(price);

            szamla = new szamla(price);

        }catch (DataBaseException dbe){
            errors.add(dbe);
        }catch (SzamlaException sze){
            errors.add(sze);
        }catch (IOException ioe){
            errors.add(new Exception("[Ár] Hiba az olvasásánál!"));
        }

        ArrayList<Integer> guiButtons = new ArrayList<Integer>(){{
            add(1); //gepek_ujgep
            add(2); //"gepek_modositas"
            add(3); //"ugyfelek_ujUgyfel"
            add(4); //"ugyfel_befizetes"
            add(5); //"ugyfel_be"
            add(6); //"ugyfel_ki"
            add(7); // gepek_torles
            add(8); // ugyfel_torles
            add(9); // ugyfel_modositas
        }};




        gui = new view(controllAction, guiButtons, tabAction, comboAction, ugyfelekListaAction);
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setVisible(true);

        //Print Exceptions...
        for(Exception ex : errors) {
            showExceptionError(ex);
        }

        updateGui();
    }

    private void updateGui(){

        try {
            gui.updateGepList(db.getGepekIDs());
            gui.updateUgyfelList(db.getUgyfelekList());
        }catch (DataBaseException dbe){
            showExceptionError(dbe);
        }

    }

    private void showExceptionError(Exception ex){
        gui.Notification(JOptionPane.ERROR_MESSAGE,"Hiba!",ex.getMessage());
    }

}

