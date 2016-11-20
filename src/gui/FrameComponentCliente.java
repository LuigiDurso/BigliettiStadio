package gui;
import progettocdr.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class FrameComponentCliente extends JFrame
{
	private JFrame framePadre,thisFrame=this;
	private Partite elencoPartite;
	private Biglietti elencoBiglietti;
	private Stadi elencoStadi;
	private Persone elencoPersone;
	private Cliente cliente;
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	private JButton buttonstadio;
	private JComboBox stadioc;
	private JButton buttondata ;
    private JTextField textdata;
	private JComboBox giorno;
	private JComboBox mese;
	private JComboBox anno;
	private String[] mesi={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	private Integer[] giorni = {31,28,31,30,31,30,31,31,30,31,30,31};
	private DefaultListModel model1,model2,model3;
	private JList list1;
	private JList list2,list3;
	
	public FrameComponentCliente(JFrame framePadre,Cliente c,Partite elencoPartite,Biglietti elencoBiglietti,Persone elencoPersone,Stadi elencoStadi)
	{
		this.elencoBiglietti=elencoBiglietti;
		this.elencoPartite=elencoPartite;
		this.elencoPersone=elencoPersone;
		this.elencoStadi=elencoStadi;
		this.cliente=c;
		this.framePadre=framePadre;
		
		
        JMenuBar bramenu = new JMenuBar();
        bramenu.add(createJMenu());
        
		this.add(createnorth(),BorderLayout.NORTH);
		this.add(createcenter(),BorderLayout.CENTER);
		this.add(createsouth(), BorderLayout.SOUTH);
		this.setSize(775,550);
		this.setJMenuBar(bramenu);
		
		class WindowsClose implements WindowListener
		{
			
			public void windowOpened(WindowEvent e) {}
			
			public void windowClosing(WindowEvent e) 
			{
				framePadre.setEnabled(true);
				thisFrame.dispose();
			}
			
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			
		}
		
		thisFrame.addWindowListener(new WindowsClose());
	}

	public JMenu createJMenu() 
	{
		JMenu menu = new JMenu("Options");
		menu.add(createJmenuItem());
		return menu;
	}

	public JMenuItem createJmenuItem() 
	{
		JMenuItem item = new JMenuItem("Disconnetti");
		
		class ItemClicked implements ActionListener
		{

			public void actionPerformed(ActionEvent arg0) 
			{
				framePadre.setEnabled(true);
				thisFrame.dispose();
				
			}
			
		}
		
		item.addActionListener(new ItemClicked());
		return item;
	}

	public JPanel createPartitePanel() 
	{
		    JPanel panel = new JPanel();
		    model1 = new DefaultListModel();
	        list1 =new JList(model1);
	        
	        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        JScrollPane jscroll=new JScrollPane(list1);
	       
	        caricamentoPartite();
	        
	        class ListListener extends MouseAdapter
			{	
				 public void mouseClicked(MouseEvent e) 
				 {  
					 if(e.getClickCount()==2)
					 {
						 Partita p=(Partita)list1.getSelectedValue();
						 try
						 {
							 if(cliente.verificaPrenotazioneAcquistoPartita(p))
							 {
								 SelezionePostiFrameComponent frame=new SelezionePostiFrameComponent(thisFrame,cliente,p,elencoBiglietti);
								 
								 thisFrame.setEnabled(false);
								 frame.setVisible(true);
								 
							 }
						 }catch(PartitaGiaPrenotataAcquistataException e1)
						 {
							 JOptionPane pane=new JOptionPane();
							 pane.showMessageDialog(null, e1.getMessage());
						 }
						 
						 
						
					 }
				 }
			}
			list1.addMouseListener(new ListListener());
	        
	        panel.add(jscroll);
	        
	        return panel;
	}

	private void caricamentoPartite() 
	{
		GregorianCalendar dataAttuale=new GregorianCalendar();
		for(Partita p:elencoPartite.getPartite())
		{
			if(dataAttuale.before(p.getData()))
			{
				model1.addElement(p);
			}
			
		}
		
	}

	public JPanel createsouth()
	{
		JPanel panel = new JPanel();
		
		 JTabbedPane tabbedPane=new JTabbedPane();
	     tabbedPane.addTab("Partite",createPartitePanel());
	     tabbedPane.addTab("Prenotazioni", createPrenotazioniPanel());
	     tabbedPane.addTab("Acquistate",createAcquistatePanel());
	     panel.add(tabbedPane,BorderLayout.EAST);
	     panel.add(createRemoveFilterButton(), BorderLayout.WEST);
	     

		return panel;
	}
	
	private JPanel createRemoveFilterButton()
	{
		JPanel pannello=new JPanel();
		
		JButton button=new JButton("REMOVE FILTER");
		
		class NoFilterListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				model1.clear();
				
				caricamentoPartite();
			}
		}
		
		button.addActionListener(new NoFilterListener());
		
		pannello.add(button);
		
		return pannello;
		
	}
	
	private JPanel createAcquistatePanel()
	{
		JPanel pannello=new JPanel();
		
		model3=new DefaultListModel();
		list3=new JList(model3);
		
		list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jScroll=new JScrollPane(list3);
		
		pannello.add(jScroll);
		
		caricamentoAcquisti();
		
		return pannello;
	}
	
	public void caricamentoAcquisti()
	{
		model3.clear();
		
		ArrayList<Biglietto> elencoAcquisti=cliente.getBigliettiAcquistati();
		
		for(Biglietto b:elencoAcquisti)
		{
			model3.addElement(b);
		}
	}

	

	public JPanel createPrenotazioniPanel() 
	{
		
		  JPanel panel = new JPanel();
		  
		    model2 = new DefaultListModel();
	        list2 =new JList(model2);
	        
	        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        JScrollPane jscroll=new JScrollPane(list2);
	       
	        caricamentoPrenotazioni();
	        
	        class ListListener extends MouseAdapter
			{	
				 public void mouseClicked(MouseEvent e) 
				 {  
					 if(e.getClickCount()==2)
					 {
						 Biglietto b=(Biglietto)list2.getSelectedValue();
						 
						 AcquistaPrenotazioneComponent frame=new AcquistaPrenotazioneComponent(thisFrame,b,elencoBiglietti);
						 
						 thisFrame.setEnabled(false);
						 frame.setVisible(true);
					 }
				 }
			}
			list2.addMouseListener(new ListListener());
	        
	        panel.add(jscroll);
	        
		return panel;
	}
	
	public void caricamentoPrenotazioni()
	{
		model2.clear();
		
		ArrayList<Biglietto> elencoPrenotazioni=cliente.getBigliettiPrenotati();
		
		int j=elencoPrenotazioni.size();
		int i=0;
		
		while(i<j)
		{
			Biglietto p=elencoPrenotazioni.get(i);
			try
			{
				if(p.controlloPartitaBiglietto())
				{
					model2.addElement(p);
					i++;
				}
				
			}catch(PartitaNonPrenotabileException ex1)
			{
				j--;
			}
			
		}
	}

	public JPanel createcenter() 
	{
		
		JPanel panel = new JPanel();
		panel.add(createDataPanel(),BorderLayout.WEST);
		panel.add(createStadiumPanel(), BorderLayout.EAST);
		panel.add(createOrdinaPanel(), BorderLayout.SOUTH);
		
		return panel;
	}

	public JPanel createOrdinaPanel() 
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Ordina per"));
		panel.setLayout(new GridLayout(1,3));
		radio1 = new JRadioButton("Cronologico");
		radio2 = new JRadioButton("ID_Stadio");
		radio3 = new JRadioButton("Lessicografico");
		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		class radioPerformed implements ActionListener
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				if(radio1.isSelected())
				{
					OrdineCronologico ordine=new OrdineCronologico();
					elencoPartite.ordinaPartite(ordine);
					model1.clear();
					caricamentoPartite();
					
				}
				if(radio2.isSelected())
				{
					OrdineStadio ordine=new OrdineStadio();
					elencoPartite.ordinaPartite(ordine);
					model1.clear();
					caricamentoPartite();
				}
				if(radio3.isSelected())
				{
					OrdineSquadre ordine=new OrdineSquadre();
					elencoPartite.ordinaPartite(ordine);
					model1.clear();
					caricamentoPartite();
				}
				
			}
			
		} 
		radio1.addActionListener(new radioPerformed());
		radio2.addActionListener(new radioPerformed());
		radio3.addActionListener(new radioPerformed());
		  panel.add(radio1);
		  panel.add(radio2);
		  panel.add(radio3);
		return panel;
	}

	public JPanel createStadiumPanel() 
	{
		JPanel panel = new JPanel();
		panel.add(createcenterstadium(),BorderLayout.CENTER);
		panel.add(createbuttonstadium(), BorderLayout.SOUTH);
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Visualizza"));
		return panel;
	}

	public JPanel createbuttonstadium() {
		JPanel panel = new JPanel();
		buttonstadio = new JButton("Filtra");
		panel.add(buttonstadio,BorderLayout.CENTER);
		class ButtonStadioPerformed implements ActionListener
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				model1.clear();
				
				Stadio s=(Stadio)stadioc.getSelectedItem();
				
				for(Partita p:elencoPartite.getPartite())
				{
					if(p.getStadio().getNome().equals(s.getNome()))
					{
						model1.addElement(p);
					}
				}
				
			}
			
		}
		buttonstadio.addActionListener(new ButtonStadioPerformed());
		return panel;
	}

	public JPanel createcenterstadium()
	{
		stadioc = new JComboBox();
		
		for(Stadio s:elencoStadi.getStadi())
		{
			stadioc.addItem(s);
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(new JLabel("Stadio:"));
		panel.add(stadioc);
		return panel;
	}

	public JPanel createDataPanel() 
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Ricerca"));
		panel.add(createcenterdata(),BorderLayout.CENTER);
		panel.add(createbutton(), BorderLayout.SOUTH);
		return panel;
	}

	public JPanel createbutton() 
	{
		JPanel panel = new JPanel();
		buttondata = new JButton("Filtra");
		panel.add(buttondata,BorderLayout.CENTER);
		
		class filtraPerformed implements ActionListener
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				model1.clear();
				
				int giornoInizio=Integer.parseInt((String)giorno.getSelectedItem());
				int meseInizio=mese.getSelectedIndex();
				int annoInizio=(int)anno.getSelectedItem();
				
				GregorianCalendar dataInizio=new GregorianCalendar(annoInizio,meseInizio,giornoInizio);
				GregorianCalendar dataFine=new GregorianCalendar(annoInizio,meseInizio,giornoInizio) ;
				dataFine.add(GregorianCalendar.DAY_OF_MONTH, 7);
				
				for(Partita p:elencoPartite.getPartite())
				{
					if(p.getData().after(dataInizio) && p.getData().before(dataFine))
					{
						model1.addElement(p);
					}
				}
				
			}
			
		}
		buttondata.addActionListener(new filtraPerformed());
		return panel;
	}

	public JPanel createcenterdata() 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,4));
		giorno = new JComboBox();
		mese = new JComboBox();
		anno = new JComboBox();
		textdata = new JTextField(5);
		GregorianCalendar data=new GregorianCalendar();
		
		int annoAttuale=data.get(GregorianCalendar.YEAR);
		for(int i=annoAttuale;i<annoAttuale+5;i++)
		{
			anno.addItem(i);
		}
		
		
		for(int i=0;i<12;i++)
		{
			mese.addItem(mesi[i]);
		}
		
		class GiorniListener implements ActionListener
		{

			int i,indice;
			
			public void actionPerformed(ActionEvent e) 
			{
				String meses = (String) mese.getSelectedItem();
				for(i=0;i<12;i++)
				{
					if(meses.equals(mesi[i]))
					{
						indice=i;

					}
				}
				int x;
				
				giorno.removeAllItems();
				
				for(x=1;x<=giorni[indice];x++)
				{
					giorno.addItem(""+x);
				}
				
				if(indice==1 && ((int)anno.getSelectedItem()%4)==0 || ((int)anno.getSelectedItem()%400)==0)
				{
					giorno.addItem(""+29);
				}

			}
		}
		
		mese.addActionListener(new GiorniListener());
		anno.addActionListener(new GiorniListener());
		
		class SettimanaListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(giorno.getSelectedItem()!=null)
				{
					int giornoInizio=Integer.parseInt((String)giorno.getSelectedItem());
					int meseInizio=mese.getSelectedIndex()+1;
					int annoInizio=(int)anno.getSelectedItem();
					
					GregorianCalendar dataInizio=new GregorianCalendar(annoInizio,meseInizio-1,giornoInizio);
					
					dataInizio.add(GregorianCalendar.DAY_OF_MONTH, 7);
					
					int meseFinale=dataInizio.get(GregorianCalendar.MONTH)+1;
					
					textdata.setText(dataInizio.get(GregorianCalendar.DAY_OF_MONTH)+"/"+meseFinale+"/"+dataInizio.get(GregorianCalendar.YEAR));
					
				}
			}
		}
		
		giorno.addActionListener(new SettimanaListener());
		
		panel.add(new JLabel("Da:"));
		panel.add(anno);
		panel.add(mese);
		panel.add(giorno);
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel("A:"));
		panel.add(textdata);
		panel.add(new JLabel());
		panel.add(new JLabel());
		return panel;
	}

	public JPanel createnorth() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,5));
		JLabel label = new JLabel("Benvenuto login@"+cliente.getLogin()); //metodo get.nome
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(label);
		return panel;
	}
}
