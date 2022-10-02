import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.*;
import java.io.*;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class frmPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scroll;
	private int row=0;
	private HashMap<Integer,Long> addresseMap;
	static BufferedReader tmpReadTxt;
	static RandomAccessFile donnee;

	/**
	 * Create the frame.
	 */
	public frmPrincipal() {
		//chargerLivres();
		//afficher();
		//remplirTable("");
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
		JButton btn =new JButton("test");
		GridBagConstraints gbc_tlBar = new GridBagConstraints();
		gbc_tlBar.insets = new Insets(0, 0, 5, 5);
		gbc_tlBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_tlBar.gridx = 0;
		gbc_tlBar.gridy = 0;
		contentPane.add(btn, gbc_tlBar);

/*		
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

		scroll = new JScrollPane(table);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		contentPane.add(scroll, gbc_table);*/
		
	}
	/*
	public String[] getListeCBox(){

		int num=0;
		String  titre = "";
		int auteur = 0;
		int annee = 0;
		int pages = 0;
		String cathegorie="";
		ArrayList<String>  liste = new ArrayList<>();
		ArrayList<String>  listeTmp = new ArrayList<>();
		String[] retour =new String[1];
		
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			donnee.seek(0);
			for (int i=0;i<donnee.length();i++){
				num = donnee.readInt();
				titre=donnee.readUTF();
				auteur=donnee.readInt();
				annee=donnee.readInt();
				pages=donnee.readInt();
				cathegorie=donnee.readUTF();
				liste.add(cathegorie);
			}
			donnee.close();
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		listeTmp.add(liste.get(0));
		for(String current:liste){
			if(listeTmp.indexOf(current)==-1){
				listeTmp.add(current);
			}
		}
		retour = new String[listeTmp.size()];
		for(int i=0;i<listeTmp.size();i++){
			retour[i]=listeTmp.get(i);
		}

		return retour;
	}*/
	public HashMap<Integer, Long> getAddresseMap() {
		return addresseMap;
	}
	public void setAddresseMap(HashMap<Integer, Long> addresseMap) {
		this.addresseMap = addresseMap;
	}

	public void chargerLivres() {
		addresseMap = new HashMap<>();

		   
			try {
				File file = new File("src\\livres.bin");
				if(file.exists()){
					String  titre = "";
					int auteur = 0;
					int annee = 0;
					int pages = 0;
					String cathegorie = "";
					donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
					donnee.seek(0);
					for (int i=0;i<donnee.length();i++){
							long adr = donnee.getFilePointer();
							int cle = donnee.readInt();
							titre=donnee.readUTF();
							auteur=donnee.readInt();
							annee=donnee.readInt();
							pages=donnee.readInt();
							cathegorie=donnee.readUTF();
							addresseMap.put(cle,adr); 
							Livre livre = new Livre(cle,titre,auteur,annee,pages,cathegorie);
					}
					donnee.close();
				}else{
					tmpReadTxt = new BufferedReader(new FileReader("src\\livres.txt"));
				    donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
					String ligne = tmpReadTxt.readLine();
					String[] elemt = new String[6];
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
						titre =elemt[1];
						auteur = Integer.parseInt(elemt[2]);
						annee = Integer.parseInt(elemt[3]);
						pages = Integer.parseInt(elemt[4]);
						cathegorie =  elemt[5];
						Long lng =donnee.getFilePointer();
						//400;Une aventure d'Astérix le gaulois. Le devin;11;1972;48;bandes dessinées 4+30+4+4+4+20=96
						addresseMap.put(num,lng);
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
					
					
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void remplirTable(String cathegorieEntree) {
		row =0;
		String[] column = {"Numero","Titre","Numero Auteur","Annee","Nombre des pages","Cathegorie"};
		DefaultTableModel model = new DefaultTableModel(column,0);
		table = new JTable(model);
		int num = 0;
		String  titre = "";
		int auteur = 0;
		int annee = 0;
		int pages = 0;
		String cathegorie = "";
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			donnee.seek(0);
			for (Integer cle:addresseMap.keySet()){
                            
				long adr = addresseMap.get(cle);
                donnee.seek(adr); 
				num=donnee.readInt();
                titre=donnee.readUTF();
                auteur=donnee.readInt();
                annee=donnee.readInt();
                pages=donnee.readInt();
                cathegorie=donnee.readUTF();
				if(cathegorieEntree.equals("")){
					model.addRow(new Object[]{String.valueOf(num),titre,String.valueOf(auteur),String.valueOf(annee),String.valueOf(pages),cathegorie});				
				}else if(cathegorie.equals(cathegorieEntree)){
					model.addRow(new Object[]{String.valueOf(num),titre,String.valueOf(auteur),String.valueOf(annee),String.valueOf(pages),cathegorie});				

				}	
				row++;
			}	
			donnee.close();				
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void afficher() {
		JTextArea retour = new JTextArea(20,95);
		retour.append("Numero\t"+ Livre.formatMot("Titre",100)+"\t\tNumero Auteur\tAnnee\tPages\tCathegorie\n");
		//String retour = "";
		int num = 0;
		String  titre = "";
		int auteur = 0;
		int annee = 0;
		int pages = 0;
		String cathegorie = "";

		
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			for (Integer cle:addresseMap.keySet())
                            {
				long adr = addresseMap.get(cle);
                donnee.seek(adr); 
                num=donnee.readInt();
                titre=donnee.readUTF();
                auteur=donnee.readInt();
                annee=donnee.readInt();
                pages=donnee.readInt();
                cathegorie=donnee.readUTF();

				Livre livre = new Livre(num,titre,auteur,annee,pages,cathegorie);
				retour.append(livre.toString());
				}
							donnee.close();
						} catch (Exception e) {
							// TODO: handle exception
						}
						JScrollPane pane = new JScrollPane(retour);
						JOptionPane.showMessageDialog(null, pane);			
	}
	public Long rechercherAddresse(int cle) {
		long adr=-1;
		System.out.println(addresseMap.size());
		for(Integer key:addresseMap.keySet()){
			if(key==cle){
				adr=addresseMap.get(key);
				break;
			}
		} 
		return adr;
		
	}
	public void suprimerCle(int cle) {
		if(rechercherAddresse(cle) != -1){
			addresseMap.remove(cle);
		}
		
	}
	public static String[] paneString(ArrayList<String> data) {
		            
		//String  titre="", cathegorie="",cle ="",auteur="",pages="",annee="";
		String[] retour = new String[6];

		Dimension d =new Dimension(150,20);
		ArrayList<JTextField> listeJtxt = new ArrayList<>();
		ArrayList<String> listeChamps= new ArrayList<String>();
		listeChamps = new ArrayList<String>(){{add("Numero");add("Titre");add("Auteur");add("Annee");add("Pages");add("Cathegorie");}};
		
		JPanel gPane = new JPanel(new GridLayout(listeChamps.size(),1));
		for(int i=0;i<listeChamps.size();i++){
			JPanel pane = new JPanel();
			JTextField jtxt = new JTextField(data.get(i));
			jtxt.setPreferredSize(d);
			JLabel lbl = new JLabel(listeChamps.get(i));
			lbl.setPreferredSize(d);
			lbl.setLabelFor(jtxt);
			listeJtxt.add(jtxt);
			pane.add(lbl);
			pane.add(jtxt);
			gPane.add(pane);

		}
		int res = JOptionPane.showConfirmDialog(null,gPane);
		if(res == JOptionPane.YES_OPTION){
			for(int i=0;i<listeJtxt.size();i++){
				retour[i]= listeJtxt.get(i).getText();
			}
		} 
		return retour;        
	
	}
	public void modifierLivre(int cle) {

		ArrayList<String> data = new ArrayList<String>(){{add("");add("");add("");add("");add("");add("");}};
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			donnee.seek(addresseMap.get(cle));
			donnee.readInt();
			donnee.readUTF();
			donnee.readInt();
			donnee.writeInt(1983);
			donnee.readInt();
			donnee.readUTF();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		
	}
	public Livre chercherLivre(int cle) {
		Livre livre = new Livre();
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			donnee.seek(addresseMap.get(cle));
			livre = new Livre(donnee.readInt(),donnee.readUTF(),donnee.readInt(),donnee.readInt(),donnee.readInt(),donnee.readUTF());
			donnee.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		return livre;
	}
	public void ajouterCle() {
		try {
			donnee = new RandomAccessFile(new File("src\\livres.bin"), "rw");
			ArrayList<String> data = new ArrayList<String>(){{add("");add("");add("");add("");add("");add("");}};
			String[] resultat = paneString(data);
			addresseMap.put(Integer.parseInt(resultat[0]), donnee.length());
			Livre livre = new Livre(Integer.parseInt(resultat[0]),resultat[1],Integer.parseInt(resultat[2])
			,Integer.parseInt(resultat[3]),Integer.parseInt(resultat[4]),resultat[0]);

				donnee.seek(donnee.length());
				donnee.writeInt(Integer.parseInt(resultat[0]));
				donnee.writeUTF(resultat[1]);
				donnee.writeInt(Integer.parseInt(resultat[2]));
				donnee.writeInt(Integer.parseInt(resultat[3]));
				donnee.writeInt(Integer.parseInt(resultat[4]));
				donnee.writeUTF(resultat[5]);
	
				donnee.close();	
				
		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}


	public static void main(String[] args) {
		//frmPrincipal frame = new frmPrincipal();
		//frame.setVisible(true);
		//System.out.println(frame.chercherLivre(1000));
		//frame.modifierLivre(300);
		//System.out.println(frame.chercherLivre(300));

		
		//System.out.println(frame.chercherLivre(300));
		//frame.suprimerCle(400);
		//System.out.println(frame.rechercherAddresse(400));
		//frame.ajouterCle();
		//System.out.println(frame.rechercherAddresse(500));
		
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
	
	public JPanel getContentPane() {
		return contentPane;
	}
	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public JScrollPane getScroll() {
		return scroll;
	}
	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}


}
