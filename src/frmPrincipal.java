import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.*;

public class frmPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public String formatMot(String mot,int max) {
		String retour ="";
		int lng = mot.length();
		if(lng>= max){
			retour = mot.substring(0, max);
		}else{
			retour = mot;
			for(int i=0;i<max-lng;i++){
				retour += " ";
			}
		}
		return retour;
	}
	public void chargerLivres() {
			try {
			BufferedReader tmpReadTxt = new BufferedReader(new FileReader("src\\livres.txt"));
			RandomAccessFile donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			String ligne = tmpReadTxt.readLine();
			String[] elemt = new String[80];
			int num = 0;
			String  titre = "";
			int auteur = 0;
			int annee = 0;
			int pages = 0;
			String cathegorie = "";
			donnee.seek(0);

			while(ligne != null){
				elemt = ligne.split(";");
				num = Integer.parseInt(elemt[0]);
				titre = formatMot(elemt[1],30);
				auteur = Integer.parseInt(elemt[2]);
				annee = Integer.parseInt(elemt[3]);
				pages = Integer.parseInt(elemt[4]);
				cathegorie = formatMot(elemt[5],20);
				
				
				//400;Une aventure d'Astérix le gaulois. Le devin;11;1972;48;bandes dessinées 4+30+4+4+4+20=96
				donnee.writeInt(num);
				donnee.writeUTF(titre);
				donnee.writeInt(auteur);
				donnee.writeInt(annee);
				donnee.writeInt(pages);
				donnee.writeUTF(cathegorie);
				
				ligne = tmpReadTxt.readLine();
			}	

				donnee.close();	
				tmpReadTxt.close();	

		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void afficher() {
		JTextArea retour = new JTextArea();
		//String retour = "";	
		try {
			RandomAccessFile donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");	
			System.out.println(donnee.getFilePointer());
			System.out.println(donnee.length());
			for (int i = 0 ; i < donnee.length() ; i++)
                            {

                            donnee.seek(i*74); 

                retour.append(String.valueOf(donnee.readInt()+" "));
                retour.append(donnee.readUTF()+" ");
                retour.append(String.valueOf(donnee.readInt()+" "));
                retour.append(String.valueOf(donnee.readInt()+" "));
                retour.append(String.valueOf(donnee.readInt()+" "));
                retour.append(donnee.readUTF()+"\n");
				System.out.println(donnee.getFilePointer());
							}
							donnee.close();
						} catch (Exception e) {
							// TODO: handle exception
						}
			
			JOptionPane.showMessageDialog(null, retour);			
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmPrincipal frame = new frmPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{266, 62, 0};
		gbl_contentPane.rowHeights = new int[]{21, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JToolBar tlBar = new JToolBar();
		tlBar.setToolTipText("Liste des livres");
		tlBar.setForeground(Color.BLACK);
		tlBar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tlBar.setBackground(Color.WHITE);
		GridBagConstraints gbc_tlBar = new GridBagConstraints();
		gbc_tlBar.insets = new Insets(0, 0, 5, 5);
		gbc_tlBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_tlBar.gridx = 0;
		gbc_tlBar.gridy = 0;
		contentPane.add(tlBar, gbc_tlBar);
		
		JComboBox cmbLivres = new JComboBox();
		tlBar.add(cmbLivres);
		cmbLivres.setModel(new DefaultComboBoxModel(new String[] {"livres", "box"}));
		cmbLivres.setToolTipText("");
		
		JComboBox cmbbCathegorie = new JComboBox();
		cmbbCathegorie.setToolTipText("");
		tlBar.add(cmbbCathegorie);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		contentPane.add(table, gbc_table);
		chargerLivres();
		afficher();
	}

}
