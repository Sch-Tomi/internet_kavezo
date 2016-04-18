package IkMen.gui;

/**
 * A program vizuális felülete.
 * <p>
 * Created by tom on 2016.03.03..
 */

import IkMen.exceptions.GuiException;
import IkMen.mysql.helpers.UgyfelArray;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class View extends JFrame {

    private JPanel mainPanel;
    private JPanel gepek;
    private JPanel ugyfelek;
    private JTabbedPane tabbedPane;

    private JButton gepek_ujgep;
    private JButton gepek_modositas;
    private JButton gepek_torles;
    private JTextArea gepek_leiras;
    private JLabel gepek_leiras_label;
    private JComboBox gepek_geplista;
    private JPanel gepek_comboButton;
    private JPanel gepek_labelPanel;
    private JPanel gepek_modPanel;

    private JPanel ugyfelek_bal;
    private JPanel ugyfelek_jobb;
    private JPanel ugyfelek_jobb_table_data;
    private JPanel ugyfelek_jobb_table_gombok;
    private JPanel ugyfelek_bal_lab;
    private JPanel ugyfelek_jobb_lab;
    private JPanel ugyfelek_jobb_end;


    private JLabel ugyfelek_ugyfelek_label;
    private JList ugyfelek_lista;
    private JScrollPane ugyfelek_listScroller;
    private DefaultListModel ugyfelek_listModel;
    private JButton ugyfelek_ujUgyfel;

    private JLabel ugyfel_ugyfel_label;
    private JTextField ugyfel_nev;
    private JLabel ugyfel_nev_lab;
    private JTextField ugyfel_cim;
    private JLabel ugyfel_cim_lab;
    private JTextField ugyfel_szemszam;
    private JLabel ugyfel_szemszam_lab;
    private JTextField ugyfel_azon;
    private JLabel ugyfel_azon_lab;
    private JTextField ugyfel_egyenleg;
    private JLabel ugyfel_egyenleg_lab;
    private JButton ugyfel_befizetes;
    private JButton ugyfel_be;
    private JButton ugyfel_ki;
    private JButton ugyfel_torles;
    private JButton ugyfel_modositas;
    private JLabel ugyfel_statusz_lab;
    private JTextField ugyfel_statusz;
    private JLabel ugyfel_pont_lab;
    private JTextField ugyfel_pont;

    private JPanel befizetesek;
    private JTable befizetesek_table;
    private JScrollPane befizetesek_table_scroll;
    private DefaultTableModel befizetesek_table_model;

    private JPanel hasznalat;
    private JTable hasznalat_table;
    private JScrollPane hasznalat_table_scroll;
    private DefaultTableModel hasznalat_table_model;

    private JPanel szamlak;
    private JList szamlak_lista;
    private JScrollPane szamlak_lista_scroll;
    private DefaultListModel szamlak_lista_model;
    private JFXPanel jfxPanel;


    public View(Action buttonAct, ArrayList<Integer> buttonCmds, Action tabAct, Action comboAct, Action ugyfelekListaAct, Action szamlakListaAct) {

        //Set Lookout
        try {
            // Set System L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
            e.printStackTrace();
            System.out.println("[LookOut] Nincs NimbusLookAndFeel téma...");
        } catch (ClassNotFoundException e) {
            // handle exception
            e.printStackTrace();
            System.out.println("[LookOut] Nincs NimbusLookAndFeel téma...");
        } catch (InstantiationException e) {
            // handle exception
            e.printStackTrace();
            System.out.println("[LookOut] Nincs NimbusLookAndFeel téma...");
        } catch (IllegalAccessException e) {
            // handle exception
            e.printStackTrace();
            System.out.println("[LookOut] Nincs NimbusLookAndFeel téma...");
        }

        tabbedPane = new JTabbedPane();

        packGepek();
        packUgyfelek();
        packBefizetesek();
        packHasznalat();
        packSzamlak();

        setActionListenersToButtons(buttonAct, buttonCmds);
        setActionListenersToTabs(tabAct);
        setGepekComboAction(comboAct);
        setUgyfelekListChangeListener(ugyfelekListaAct);
        setSzamlakListChangeListener(szamlakListaAct);

        tabbedPane.addTab("Gépek", gepek);
        tabbedPane.addTab("Ügyfelek", ugyfelek);
        tabbedPane.addTab("Befizetesek", befizetesek);
        tabbedPane.addTab("Használat", hasznalat);
        tabbedPane.addTab("Számlák", szamlak);


        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new DimensionUIResource(640, 480));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(tabbedPane);

        getContentPane().add(mainPanel);
        setTitle("Internet Kávézó Menedzser");
        setSize(new Dimension(640, 480));
        setLocationRelativeTo(null); // középre nyíljon meg

        String path = System.getProperty("user.dir")+"/cfg/services_coffee.png";
        ImageIcon img = new ImageIcon(path);
        setIconImage(img.getImage());

    }


    public void packGepek() {
        gepek = new JPanel();
        gepek.setLayout(new BoxLayout(gepek, BoxLayout.Y_AXIS));


        // Gépek Fül
        gepek_ujgep = new JButton();
        gepek_ujgep.setText("Új gép");

        gepek_modositas = new JButton();
        gepek_modositas.setText("Módosítás");

        gepek_torles = new JButton();
        gepek_torles.setText("Törlés");

        gepek_leiras = new JTextArea();

        gepek_leiras_label = new JLabel();
        gepek_leiras_label.setText("Gép Leírás:");

        gepek_geplista = new JComboBox();

        gepek_comboButton = new JPanel();
        gepek_comboButton.setLayout(new BoxLayout(gepek_comboButton, BoxLayout.X_AXIS));

        gepek_comboButton.add(gepek_geplista);
        gepek_comboButton.add(gepek_ujgep);

        gepek_labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gepek_labelPanel.add(gepek_leiras_label);

        gepek_modPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gepek_modPanel.add(gepek_torles);
        gepek_modPanel.add(gepek_modositas);

        //feltöltés
        gepek.add(gepek_comboButton);
        gepek.add(gepek_labelPanel);
        gepek.add(gepek_leiras);
        gepek.add(gepek_modPanel);


    }

    public void packUgyfelek() {

        ugyfelek = new JPanel();
        ugyfelek.setLayout(new BoxLayout(ugyfelek, BoxLayout.X_AXIS));

        ugyfelek_bal = new JPanel();
        ugyfelek_bal.setLayout(new BorderLayout());
        ugyfelek_jobb = new JPanel();
        ugyfelek_jobb.setLayout(new BorderLayout());
        ugyfelek_jobb_table_data = new JPanel();
        ugyfelek_jobb_table_gombok = new JPanel();


        ugyfelek_bal_lab = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ugyfelek_jobb_lab = new JPanel(new FlowLayout(FlowLayout.CENTER));

        ugyfelek_ugyfelek_label = new JLabel("Ügyfelek");

        ugyfelek_listModel = new DefaultListModel();

        ugyfelek_lista = new JList(ugyfelek_listModel);
        ugyfelek_lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ugyfelek_lista.setLayoutOrientation(JList.VERTICAL);
        ugyfelek_lista.setVisibleRowCount(-1);

        ugyfelek_listScroller = new JScrollPane(ugyfelek_lista);
        ugyfelek_listScroller.setSize(new Dimension(250, 80));

        ugyfelek_ujUgyfel = new JButton("Új ügyfél");

        ugyfel_ugyfel_label = new JLabel("Ügyfél");
        ugyfel_nev = new JTextField();
        ugyfel_nev_lab = new JLabel("Neve:");
        ugyfel_cim = new JTextField();
        ugyfel_cim_lab = new JLabel("Címe:");
        ugyfel_szemszam = new JTextField();
        ugyfel_szemszam_lab = new JLabel("Szem. szám:");
        ugyfel_azon = new JTextField();
        ugyfel_azon_lab = new JLabel("Azonosító:");
        ugyfel_egyenleg = new JTextField();
        ugyfel_egyenleg_lab = new JLabel("Egyenleg:");
        ugyfel_befizetes = new JButton("Befizetés");
        ugyfel_be = new JButton("Beléptetés");
        ugyfel_ki = new JButton("Kiléptetés");
        ugyfel_torles = new JButton("Törlés");
        ugyfel_modositas = new JButton("Módosítás");

        ugyfel_statusz_lab = new JLabel("Állapota:");
        ugyfel_statusz = new JTextField("");

        ugyfel_pont_lab = new JLabel("Pontjai:");
        ugyfel_pont = new JTextField("");

        // Baloldal
        ugyfelek_bal_lab.add(ugyfelek_ugyfelek_label);
        ugyfelek_bal.add(ugyfelek_bal_lab, BorderLayout.PAGE_START);
        ugyfelek_bal.add(ugyfelek_lista, BorderLayout.CENTER);
        ugyfelek_bal.add(ugyfelek_ujUgyfel, BorderLayout.PAGE_END);

        // Jobboldal

        //--- DATA

        GridBagLayout layout = new GridBagLayout();
        ugyfelek_jobb_table_data.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_nev_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_nev, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_cim_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_cim, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_szemszam_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_szemszam, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_azon_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_azon, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_statusz_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_statusz, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_pont_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_pont, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.1;
        ugyfelek_jobb_table_data.add(ugyfel_egyenleg_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        ugyfelek_jobb_table_data.add(ugyfel_egyenleg, gbc);

        // --- DATA

        // GOMBOK

        GridBagLayout layout_g = new GridBagLayout();
        ugyfelek_jobb_table_gombok.setLayout(layout_g);
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.weightx = 1;

        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        ugyfelek_jobb_table_gombok.add(ugyfel_befizetes, gbc2);


        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        ugyfelek_jobb_table_gombok.add(ugyfel_modositas, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 1;
        ugyfelek_jobb_table_gombok.add(ugyfel_torles, gbc2);

        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        ugyfelek_jobb_table_gombok.add(ugyfel_be, gbc2);

        gbc2.gridx = 1;
        gbc2.gridy = 2;
        ugyfelek_jobb_table_gombok.add(ugyfel_ki, gbc2);


        ugyfelek_jobb_lab.add(ugyfel_ugyfel_label);
        ugyfelek_jobb.add(ugyfelek_jobb_lab, BorderLayout.PAGE_START);
        ugyfelek_jobb.add(ugyfelek_jobb_table_data, BorderLayout.CENTER);
        ugyfelek_jobb.add(ugyfelek_jobb_table_gombok, BorderLayout.PAGE_END);


//        ugyfelek_jobb_end = new JPanel();
//        ugyfelek_jobb_end.add(ugyfel_be);
//        ugyfelek_jobb_end.add(ugyfel_ki);
//        ugyfelek_jobb.add(ugyfelek_jobb_end, BorderLayout.PAGE_END);

        ugyfelek.add(ugyfelek_bal);
        ugyfelek.add(new JSeparator(JSeparator.VERTICAL));
        ugyfelek.add(ugyfelek_jobb);
    }

    public void packBefizetesek() {

        Object[][] data = {};
        String[] columnNames = {"Ügyfél azonosító", "Befizetett összeg"};
        befizetesek_table_model = new DefaultTableModel(data, columnNames)  {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };


        befizetesek_table = new JTable(befizetesek_table_model) ;
        befizetesek_table_scroll = new JScrollPane(befizetesek_table);
        befizetesek_table.setFillsViewportHeight(true);

        befizetesek = new JPanel();
        befizetesek.setLayout(new BorderLayout());
        befizetesek.add(befizetesek_table.getTableHeader(), BorderLayout.PAGE_START);
        befizetesek.add(befizetesek_table, BorderLayout.CENTER);

    }

    public void packHasznalat() {

        Object[][] data = {};
        String[] columnNames = {"Ügyfél azonosító", "Számítógép azonosító", "Bejelentkezés időpontja", "Kijelentkezés időpontja"};
        hasznalat_table_model = new DefaultTableModel(data, columnNames)  {
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to be not editable
            }
        };


        hasznalat_table = new JTable(hasznalat_table_model) ;
        hasznalat_table_scroll = new JScrollPane(hasznalat_table);
        hasznalat_table.setFillsViewportHeight(true);

        hasznalat = new JPanel();
        hasznalat.setLayout(new BorderLayout());
        hasznalat.add(hasznalat_table.getTableHeader(), BorderLayout.PAGE_START);
        hasznalat.add(hasznalat_table, BorderLayout.CENTER);


    }

    public void packSzamlak() {
        szamlak = new JPanel();
        szamlak.setLayout(new GridLayout(1, 3, 5, 5));

        szamlak_lista_model = new DefaultListModel();

        szamlak_lista = new JList(szamlak_lista_model);
        szamlak_lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        szamlak_lista.setLayoutOrientation(JList.VERTICAL);
        szamlak_lista.setVisibleRowCount(-1);

        szamlak_lista_scroll = new JScrollPane(szamlak_lista);
        szamlak_lista_scroll.setSize(new Dimension(250, 80));

        jfxPanel = new JFXPanel();

        //JPanel svP = new JPanel();
        //svP.add(szamlak_lista);

        szamlak.add(szamlak_lista);
        //Szamlak.add(new JSeparator(JSeparator.VERTICAL));
        szamlak.add(jfxPanel);

    }


    public void setActionListenersToButtons(Action buttonAct, ArrayList<Integer> buttonCmds) {

        ArrayList<JButton> guiButtons = new ArrayList<JButton>() {{
            add(gepek_ujgep);
            add(gepek_modositas);
            add(ugyfelek_ujUgyfel);
            add(ugyfel_befizetes);
            add(ugyfel_be);
            add(ugyfel_ki);
            add(gepek_torles);
            add(ugyfel_torles);
            add(ugyfel_modositas);
        }};


        for (int i = 0; i < buttonCmds.size(); i++) {

            setButtonAction(guiButtons.get(i), buttonAct, buttonCmds.get(i));

        }

    }

    public void setButtonAction(JButton button, final Action act, final Integer cmdInt) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                act.actionPerformed(new ActionEvent(e.getSource(), e.getID(), Integer.toString(cmdInt)));
            }
        });


    }

    public void setActionListenersToTabs(final Action tabAct) {

        tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    tabAct.actionPerformed(new ActionEvent(e.getSource(), 1, Integer.toString(pane.getSelectedIndex())));
                }
            }
        });


    }

    public void updateGepList(ArrayList<String> list) {

        gepek_geplista.removeAllItems();

        for (String aList : list) {

            gepek_geplista.addItem(aList);
        }

    }

    public void setGepekComboAction(final Action act) {

        gepek_geplista.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent arg0) {
                act.actionPerformed(new ActionEvent(arg0.getSource(), arg0.getID(), String.valueOf(gepek_geplista.getSelectedItem())));
            }
        });

    }

    public void updateLeiras(ArrayList<String> data) {
        if (data.size() == 3) {
            String azon = data.get(0);
            String leiras = data.get(1);
            int status = Integer.parseInt(data.get(2));

            gepek_leiras.setText(leiras);

            if (status == 0) {
                gepek_leiras_label.setText("Gép Leírás: (A gép Szabad)");
            }

            if (status == 1) {
                gepek_leiras_label.setText("Gép Leírás: (A gép Foglalt)");
            }
        }
    }

    public String getCurrentGepItem() {
        return String.valueOf(gepek_geplista.getSelectedItem());
    }

    public String getLeiras() {
        return gepek_leiras.getText();
    }

    public ArrayList<String> makeNewMachine() {

        JTextField azon = new JTextField();
        JTextArea leiras = new JTextArea(20, 50);
        JPanel inside = new JPanel();
        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.add(new JLabel("Azonosító"));
        inside.add(azon);
        inside.add(new JLabel("Gép leírása"));
        inside.add(leiras);
        inside.setSize(new Dimension(300, 200));


        JOptionPane.showMessageDialog(null, inside, "Új gép felvétele", JOptionPane.PLAIN_MESSAGE);

        ArrayList<String> retarr = new ArrayList<String>();
        retarr.add(azon.getText());
        retarr.add(leiras.getText());

        return retarr;

    }


    // Ugyfelek

    public void updateUgyfelList(ArrayList<String> list) {

        clearUgyfelDatas();
        ugyfelek_listModel.removeAllElements();

        for (String ListItem : list) {
            ugyfelek_listModel.addElement(ListItem);
        }

        ugyfelek_lista.updateUI();

    }

    public void setUgyfelekListChangeListener(final Action act) {

        ugyfelek_lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (getCurrentUgyfel() != null) {
                    act.actionPerformed(new ActionEvent(e.getSource(), 1, getCurrentUgyfel()));
                }
            }
        });


    }

    public String getCurrentUgyfel() {

        if (ugyfelek_lista.getSelectedValue() != null) {
            return ugyfelek_lista.getSelectedValue().toString();
        } else {
            return null;
        }

    }

    public void updateUgyfelDatas(UgyfelArray data) {

        ugyfel_nev.setText(data.nev);
        ugyfel_cim.setText(data.cim);
        ugyfel_szemszam.setText(data.szemSzam);
        ugyfel_egyenleg.setText(Integer.toString(data.egyenleg));
        ugyfel_azon.setText(data.azon);
        ugyfel_azon.setEditable(false);
        ugyfel_egyenleg.setEditable(false);
        ugyfel_pont.setText(Integer.toString(data.pont));
        ugyfel_pont.setEditable(false);

        if (data.status == 1) {
            ugyfel_statusz.setText("Bejelentkezve - " + data.gepid);
        } else {
            ugyfel_statusz.setText("Kijelentkezve");
        }
        ugyfel_statusz.setEditable(false);


    }

    public void clearUgyfelDatas() {
        ugyfel_nev.setText("");
        ugyfel_cim.setText("");
        ugyfel_szemszam.setText("");
        ugyfel_egyenleg.setText("");
        ugyfel_azon.setText("");
        ugyfel_azon.setEditable(true);
        ugyfel_statusz.setText("");
        ugyfel_statusz.setEditable(true);
        ugyfel_pont.setText("");
        ugyfel_pont.setEditable(true);
    }

    public int befizetes() {


        // Kérdés ablak...
        JTextField osszeg = new JTextField();
        JPanel inside = new JPanel();
        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.add(new JLabel("Befizetni kívánt összeg:"));
        inside.add(osszeg);
        inside.setSize(new Dimension(300, 200));


        JOptionPane.showMessageDialog(null, inside, "Új befizetés", JOptionPane.PLAIN_MESSAGE);


        // megerősítés
        Object[] options = {"Igen",
                "Nem"};
        int n = JOptionPane.showOptionDialog(null,
                new StringBuilder("Biztos ezt az összeget akarja hozzáadni, a ").append(getCurrentUgyfel()).append(" nevű felhasználó számlájához?").append("\nÖsszeg: ").append(osszeg.getText()).toString(),
                "Befizetés megerősítése",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);


        // válasz alapján:
        int ret = 0;

        if (n == 0) {
            try {
                ret = Integer.parseInt(osszeg.getText());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                throw new GuiException("[GUI] Nem számot adott meg!");
            }
        }

        return ret;
    }

    public UgyfelArray newUser() {

        JTextField nev = new JTextField();
        JTextField cim = new JTextField();
        JTextField szemszam = new JTextField();
        JTextField azon = new JTextField();
        JPanel inside = new JPanel();

        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.add(new JLabel("Adja meg az új ügyfél adatait:"));
        inside.add(new JLabel("Neve:"));
        inside.add(nev);
        inside.add(new JLabel("Címe:"));
        inside.add(cim);
        inside.add(new JLabel("Személyi száma:"));
        inside.add(szemszam);
        inside.add(new JLabel("Azonosító:"));
        inside.add(azon);

        JOptionPane.showMessageDialog(null, inside, "Új ügyfél", JOptionPane.PLAIN_MESSAGE);

        return new UgyfelArray(nev.getText(), azon.getText(), cim.getText(), szemszam.getText(), "", 0, 0, "", 0);

    }

    public String UgyfelBe(ArrayList<String> gepids) {

        JComboBox<String> combo = new JComboBox<String>();

        for (String gep : gepids) {
            combo.addItem(gep);
        }

        JPanel inside = new JPanel();
        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.setSize(new Dimension(300, 200));
        inside.add(new JLabel("Válasszon egy gépet:"));
        inside.add(combo);

        JOptionPane.showMessageDialog(null, inside, "Ügyfél beléptetés", JOptionPane.PLAIN_MESSAGE);

        return (String) combo.getSelectedItem();
    }

    public UgyfelArray getCurrentUgyfelData() {
        return new UgyfelArray(ugyfel_nev.getText(), ugyfel_azon.getText(), ugyfel_cim.getText(), ugyfel_szemszam.getText(), "", 0, 0, "", 0);
    }

    // Befizetesek
    public void updateBefizetesekList(ArrayList<ArrayList<String>> list) {

        befizetesek_table_model.setRowCount(0);

        for(ArrayList<String> arr : list){
            befizetesek_table_model.addRow(arr.toArray());
        }

        befizetesek_table.updateUI();
    }

    //Használat
    public void updateHasznalatList(ArrayList<ArrayList<String>> list) {

        hasznalat_table_model.setRowCount(0);

        for(ArrayList<String> arr : list){
            hasznalat_table_model.addRow(arr.toArray());
        }

        hasznalat_table.updateUI();

    }

    //Számlák
    public void setSzamlakListChangeListener(final Action act) {

        szamlak_lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (getCurrentSzamla() != null) {
                    act.actionPerformed(new ActionEvent(e.getSource(), 1, getCurrentSzamla()));
                }
            }
        });


    }

    public String getCurrentSzamla() {

        if (szamlak_lista.getSelectedValue() != null) {
            return szamlak_lista.getSelectedValue().toString();
        } else {
            return null;
        }

    }

    public void updateSzamlak(ArrayList<String> list) {

        szamlak_lista_model.removeAllElements();

        for (String item : list) {
            szamlak_lista_model.addElement(item);
        }

        szamlak_lista.updateUI();
    }

    public void showSzamla(String html) {

        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().loadContent(html);
        });

    }


    // OTHER

    /**

     Used for error messages.
     ERROR_MESSAGE = 0;

     Used for information messages.
     INFORMATION_MESSAGE = 1;

     Used for warning messages.
     WARNING_MESSAGE = 2;

     Used for questions.
     QUESTION_MESSAGE = 3;
     */
    public void Notification(int type, String title, String msg) {

        JOptionPane.showMessageDialog(null,
                msg,
                title,
                type);
    }


}
