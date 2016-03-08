package IkMen.gui; /**
 * Created by tom on 2016.03.03..
 */

import IkMen.mysql.helpers.ugyfelArray;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class view extends JFrame{

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
    private JPanel ugyfelek_bal_lab;
    private JPanel ugyfelek_jobb_lab;

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



    public view(Action buttonAct, ArrayList<Integer> buttonCmds, Action tabAct, Action comboAct, Action ugyfelekListaAct){

        tabbedPane = new JTabbedPane();

        packGepek();
        packUgyfelek();

        setActionListenersToButtons(buttonAct, buttonCmds);
        setActionListenersToTabs(tabAct);
        setGepekComboAction(comboAct);
        setUgyfelekListChangeListener(ugyfelekListaAct);

        tabbedPane.addTab("Gépek", gepek);
        tabbedPane.addTab("Ügyfelek", ugyfelek);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new DimensionUIResource(640,480));
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(tabbedPane);

        getContentPane().add(mainPanel);
        setTitle("Internet Kávézó Menedzser");
        setSize(new Dimension(640,480));
        setLocationRelativeTo(null); // középre nyíljon meg

    }




    public void packGepek(){
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

    public void packUgyfelek(){

        ugyfelek = new JPanel();
        ugyfelek.setLayout(new BoxLayout(ugyfelek, BoxLayout.X_AXIS));

        ugyfelek_bal = new JPanel();
        ugyfelek_bal.setLayout(new BoxLayout(ugyfelek_bal, BoxLayout.Y_AXIS));
        ugyfelek_jobb = new JPanel();

        ugyfelek_bal_lab = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ugyfelek_jobb_lab = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ugyfelek_ugyfelek_label = new JLabel("Ügyfelek");

        ugyfelek_listModel = new DefaultListModel();
        ugyfelek_listModel.addElement("123");
        ugyfelek_listModel.addElement("1265");
        ugyfelek_listModel.addElement("221");

        ugyfelek_lista = new JList(ugyfelek_listModel);
        ugyfelek_lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ugyfelek_lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ugyfelek_lista.setVisibleRowCount(-1);

        ugyfelek_listScroller = new JScrollPane(ugyfelek_lista);
        ugyfelek_listScroller.setPreferredSize(new Dimension(250, 80));

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


        // Baloldal
        ugyfelek_bal_lab.add(ugyfelek_ugyfelek_label);
        ugyfelek_bal.add(ugyfelek_bal_lab);
        ugyfelek_bal.add(ugyfelek_lista);
        ugyfelek_bal.add(ugyfelek_ujUgyfel);

        // Jobboldal
        GridBagLayout layout = new GridBagLayout();
        ugyfelek_jobb.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        ugyfelek_jobb_lab.add(ugyfel_ugyfel_label);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 30;
        ugyfelek_jobb.add(ugyfelek_jobb_lab, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        ugyfelek_jobb.add(ugyfel_nev_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        ugyfelek_jobb.add(ugyfel_nev, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        ugyfelek_jobb.add(ugyfel_cim_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        ugyfelek_jobb.add(ugyfel_cim, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        ugyfelek_jobb.add(ugyfel_szemszam_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        ugyfelek_jobb.add(ugyfel_szemszam, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        ugyfelek_jobb.add(ugyfel_azon_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        ugyfelek_jobb.add(ugyfel_azon, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        ugyfelek_jobb.add(ugyfel_egyenleg_lab, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        ugyfelek_jobb.add(ugyfel_egyenleg, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 6;
        ugyfelek_jobb.add(ugyfel_befizetes, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 30;
        gbc.gridx = 0;
        gbc.gridy = 8;
        ugyfelek_jobb.add(ugyfel_be, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        ugyfelek_jobb.add(ugyfel_ki, gbc);

        ugyfelek.add(ugyfelek_bal);
        ugyfelek.add(ugyfelek_jobb);
    }

    public void setActionListenersToButtons(Action buttonAct, ArrayList<Integer> buttonCmds){

        ArrayList<JButton> guiButtons = new ArrayList<JButton>(){{
            add(gepek_ujgep);
            add(gepek_modositas);
            add(ugyfelek_ujUgyfel);
            add(ugyfel_befizetes);
            add(ugyfel_be);
            add(ugyfel_ki);
            add(gepek_torles);
        }};


        for(int i=0; i<buttonCmds.size(); i++){

            setButtonAction(guiButtons.get(i), buttonAct, buttonCmds.get(i));

        }

    }

    public void setButtonAction(JButton button, final Action act, final Integer cmdInt){

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                act.actionPerformed(new ActionEvent(e.getSource(), e.getID(), Integer.toString(cmdInt)));
            }
        });


    }

    public void setActionListenersToTabs(final Action tabAct){

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

    public void updateGepList(ArrayList<String> list){

        gepek_geplista.removeAllItems();

        for (String aList : list) {

            gepek_geplista.addItem(aList);
        }

    }

    public void setGepekComboAction(final Action act){

        gepek_geplista.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent arg0) {
                act.actionPerformed( new ActionEvent(arg0.getSource(), arg0.getID(), String.valueOf(gepek_geplista.getSelectedItem())) );
            }
        });

    }

    public void updateLeiras(ArrayList<String> data){
        if(data.size() == 3) {
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

    public String getCurrentGepItem(){
        return String.valueOf(gepek_geplista.getSelectedItem());
    }

    public String getLeiras(){
        return gepek_leiras.getText();
    }

    public ArrayList<String> makeNewMachine(){

        JTextField azon = new JTextField();
        JTextArea leiras = new JTextArea(20,50);
        JPanel inside = new JPanel();
        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.add(new JLabel("Azonosító"));
        inside.add(azon);
        inside.add(new JLabel("Gép leírása"));
        inside.add(leiras);
        inside.setSize(new Dimension(300,200));


        JOptionPane.showMessageDialog(null, inside, "Új gép felvétele", JOptionPane.PLAIN_MESSAGE);

        ArrayList<String> retarr =  new ArrayList<String>();
        retarr.add(azon.getText());
        retarr.add(leiras.getText());

        return retarr;

    }


    // Ugyfelek

    public void updateUgyfelList(ArrayList<String> list){

        ugyfelek_listModel.removeAllElements();

        for (String ListItem : list) {
            ugyfelek_listModel.addElement(ListItem);
        }

        ugyfelek_lista.updateUI();

    }

    public void setUgyfelekListChangeListener(Action act){

        ugyfelek_lista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                act.actionPerformed(new ActionEvent(e.getSource(), 1, ugyfelekGetCurrent()));
            }
        });


    }

    public String ugyfelekGetCurrent(){

        return ugyfelek_lista.getSelectedValue().toString();

    }

    public void updateUgyfelDatas(ugyfelArray data){

        ugyfel_nev.setText(data.nev);
        ugyfel_cim.setText(data.cim);
        ugyfel_szemszam.setText(data.szemSzam);
        ugyfel_egyenleg.setText(Integer.toString(data.egyenleg));

    }

    public int befizetes(){


        // Kérdés ablak...
        JTextField osszeg = new JTextField();
        JPanel inside = new JPanel();
        inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
        inside.add(new JLabel("Befizetni kívánt összeg:"));
        inside.add(osszeg);
        inside.setSize(new Dimension(300,200));


        JOptionPane.showMessageDialog(null, inside, "Új befizetés", JOptionPane.PLAIN_MESSAGE);


        // megerősítés
        Object[] options = {"Igen",
                            "Nem"};
        int n = JOptionPane.showOptionDialog(null,
        new StringBuilder("Biztos ezt az összeget akarja hozzáadni, a ").append("NEVE").append(" nevű felhasználó számlájához?").append("\nÖsszeg: ").append(osszeg.getText()).toString(),
        "Befizetés megerősítése",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]);


        // válasz alapján:
        int ret = 0;

        if(n == 0){
             ret = Integer.parseInt(osszeg.getText());
        }

        return ret;
    }



}
