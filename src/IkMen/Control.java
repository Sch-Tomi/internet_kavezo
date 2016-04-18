package IkMen;

/**
 *
 * A kezelő osztály, összeköti a GUI-t és az adadtbázis elérést.
 *
 * Created by tom on 2016.03.04..
 */


import IkMen.exceptions.*;
import IkMen.gui.View;
import IkMen.mysql.helpers.KilepesAdatok;
import IkMen.mysql.helpers.UgyfelArray;
import IkMen.mysql.Model;
import IkMen.tools.ConfigReader;
import IkMen.tools.Szamla;
import IkMen.tools.Szamlak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Control {

    private View gui;
    private Model db;
    private Szamla szamla;
    private Szamlak szamlak;
    private ConfigReader config;

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
                        case 3: // használat
                            gui.updateHasznalatList(db.getHasznalatList());
                            break;
                        case 4: // Számlák
                            gui.updateSzamlak(szamlak.getList());
                            break;
                    }
                }catch (DataBaseException dbe){
                    showExceptionError(dbe);
                }catch (SzamlakException szke){
                    showExceptionError(szke);
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
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél számlájához hozzáadtuk az összeget!");
                        break;

                    case 5: // "ugyfel_be":
                        String uresgep = gui.UgyfelBe(db.getUresGepekIDs());
                        db.ugyfelBe(gui.getCurrentUgyfel(), uresgep);
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél sikerült beléptetni!");
                        break;

                    case 6: //"ugyfel_ki":

                        KilepesAdatok data = db.getUgyfelKiAdat(gui.getCurrentUgyfel());

                        if(data.fizetendo <= data.egyenleg){
                            db.setEgyenleg(data.azon,-data.fizetendo);
                            szamla.create_szamla(data);
                            db.ugyfelKi(data);

                            gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Az ügyfél sikeresen kilépett a számla a számla fülön megtekinthető!");
                        }else{
                            gui.Notification(JOptionPane.WARNING_MESSAGE, "Kiléptetési Hiba!", "A felhasználó számláján nincs elég pénz.");
                        }

                        break;

                    case 7:
                        db.deleteGep(gui.getCurrentGepItem());
                        updateGui();
                        gui.Notification(JOptionPane.INFORMATION_MESSAGE, "Sikeres Művelet", "Gép törölve!");
                        break;

                    case 8:
                        UgyfelArray delData =db.getUgyfel(gui.getCurrentUgyfel());

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
                        UgyfelArray oldData = db.getUgyfel(gui.getCurrentUgyfel());
                        UgyfelArray currentData = gui.getCurrentUgyfelData();

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

    public Action szamlakListaAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                gui.showSzamla(szamlak.getHtml(gui.getCurrentSzamla()));
            }catch (SzamlakException szke){
                showExceptionError(szke);
            }

        }
    };


    public Control(){

        int price;

        ArrayList<Exception> errors = new ArrayList<>();

        try {

            config = new ConfigReader();
            db = new Model(config.getPRICE(), config.getDB_URL(), config.getDB_USER(), config.getDB_PASS());
            szamla = new Szamla(config.getPRICE());
            szamlak = new Szamlak();

        }catch (DataBaseException dbe){
            errors.add(dbe);
        }catch (SzamlaException sze){
            errors.add(sze);
        }catch (SzamlakException szke){
            errors.add(szke);
        }catch (ConfigException cfge){
            errors.add(cfge);
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




        gui = new View(controllAction, guiButtons, tabAction, comboAction, ugyfelekListaAction, szamlakListaAction);
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setVisible(true);

        //Print Exceptions...
        errors.forEach(this::showExceptionError);

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

