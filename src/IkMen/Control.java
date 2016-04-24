package IkMen;

/**
 *
 * A kezelő osztály, összeköti a GUI-t és az adadtbázis elérést.
 *
 *
 * Internet Kávézó Menedzser - Internet kávézó gépbérlés nyílván tartása
 * Copyright (C) 2016  Schronk Tamás
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
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

            config = new ConfigReader(System.getProperty("user.dir")+"/cfg/config.txt");
            db = new Model(config.getPRICE(), config.getDB_URL(), config.getDB_USER(), config.getDB_PASS());
            szamla = new Szamla(config.getPRICE(),System.getProperty("user.dir")+"/cfg/template.html");
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

