/**
 * Created by tom on 2016.03.03..
 */

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;

public class gui extends JFrame{

    private JPanel mainPanel;
    private JPanel gepek;
    private JPanel ugyfelek;
    private JTabbedPane tabbedPane;

    private JButton gepek_ujgep;
    private JButton gepek_modositas;
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



    public gui(){

        initUI();

    }

    private void initUI(){

        tabbedPane = new JTabbedPane();

        packGepek();
        packUgyfelek();

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
        ugyfelek_lista = new JList();
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

}
