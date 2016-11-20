package gui;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import progettocdr.*;

public class GestoreFrameComponent extends JFrame
{
	private Partite elencoPartite;
	private Biglietti elencoBiglietti;
	private JFrame thisFrame=this;
	private JFrame framePadre;
	private JRadioButton radio1,radio2;
	private DefaultListModel model;
	private JTextField squadraA,squadraB,prezzo;
	private JComboBox giorno,mese,anno,stadio,stadi,stadi1,stadi2,ora,minuto;
	private GregorianCalendar data;
	private int indice;
	private JLabel totale1;
	private Stadi elencoStadi;
	private static final String DEFAULT_TEXT="Totale Incassi:        "; 
	private String[] mesi={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	private Integer[] giorni = {31,28,31,30,31,30,31,31,30,31,30,31};
	private final int FRAME_WIDTH=700,FRAME_HEIGHT=500;
	
	public GestoreFrameComponent(JFrame framePadre,Gestore g,Partite elencoPartite,Biglietti elencoBiglietti,Stadi elencoStadi)
	{
		Gestore gestore=g;
		this.elencoStadi=elencoStadi;
		this.elencoBiglietti=elencoBiglietti;
		this.elencoPartite=elencoPartite;
		this.framePadre=framePadre;
		
		JTabbedPane tabbedPane=new JTabbedPane();
		tabbedPane.addTab("Inserisci Partita",createInserisciPartitaPanel());
		tabbedPane.addTab("Gestisci Stadio", createStadioPanel());
		tabbedPane.addTab("Gestisci Sconti", createGestisciScontiPanel());
		thisFrame.add(tabbedPane);
		
		JLabel label=new JLabel();
		label.setText("Benvenuto nel sistema sg."+g.getNome()+" "+g.getCognome());
		label.setAlignmentX(RIGHT_ALIGNMENT);
		thisFrame.add(label,BorderLayout.SOUTH);
		
		thisFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
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

	private Component createGestisciScontiPanel() 
	{
		JPanel pannello=new JPanel();
		
		pannello.add(createRadioPanel(),BorderLayout.NORTH);
		pannello.add(createJListPanel(),BorderLayout.CENTER);
		
		return pannello;
		
	}
	
	private JPanel createJListPanel()
	{
		JPanel pannello=new JPanel();
		
		model = new DefaultListModel();
		JList list=new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane jscroll=new JScrollPane(list);
		
		class ListListener extends MouseAdapter
		{	
			 public void mouseClicked(MouseEvent e) 
			 {  
				 if(e.getClickCount()==2)
				 {
					 Partita p=(Partita)list.getSelectedValue();
					 ScontoFrameComponent frame=new ScontoFrameComponent(thisFrame,p);
					 thisFrame.setEnabled(false);
					 frame.setVisible(true);
				 }
			 }
		}
		list.addMouseListener(new ListListener());
		
		pannello.add(jscroll);
		
		inserisciPariteJList();
		
		return pannello;
	}
	
	private void inserisciPariteJList()
	{
		for(Partita p: elencoPartite.getPartite())
		{
			model.addElement(p);
		}
	}
	
	private JPanel createRadioPanel()
	{
		JPanel pannello=new JPanel();
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Ordine delle partite"));

		radio1=new JRadioButton("Cronologico");
		radio2=new JRadioButton("Capienza");
		
		ButtonGroup groupButton=new ButtonGroup();
		groupButton.add(radio1);
		groupButton.add(radio2);
		
		pannello.add(radio1);
		pannello.add(radio2);
		
		class RadioListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(radio1.isSelected())
				{
					OrdineCronologico ordine=new OrdineCronologico();
					elencoPartite.ordinaPartite(ordine);
					model.clear();
					inserisciPariteJList();
					
				}
				else
				{
					OrdineCapienzaStadi ordine=new OrdineCapienzaStadi();
					elencoPartite.ordinaPartite(ordine);
					model.clear();
					inserisciPariteJList();
				}
			}		
		}
		
		radio1.addActionListener(new RadioListener());
		radio2.addActionListener(new RadioListener());
		
		
		return pannello;
	}

	private JPanel createStadioPanel() 
	{
		JPanel panel=new JPanel();
        
        
        JPanel panelNorth=new JPanel();
        panelNorth.setLayout(new GridLayout(1,3));
        JPanel panelCenter=new JPanel();
        panelCenter.setLayout(new GridLayout(2,2));
        JPanel panelSouth=new JPanel();
        panelSouth.setLayout(new GridLayout(2,3));
         
         
         
        //panelStadio ---NORTH
        JPanel panelStadioN=new JPanel();
        stadi=new JComboBox();
        ArrayList<Stadio> arrayStadi=elencoStadi.getStadi();
            for(Stadio elements:arrayStadi){
                stadi.addItem(elements.toString());
            }
            stadi.addItem("Tutti");
        panelStadioN.add(stadi);
         
         
        //label totale---NORTH
        JPanel totaleN=new JPanel();
        totale1=new JLabel();
        totale1.setText(DEFAULT_TEXT);
        totaleN.add(totale1);
         
        //button totale----NORTH
        JPanel buttonN=new JPanel();
        final JButton buttonN1=new JButton("Calcola Totale");
        buttonN.add(buttonN1);
         
        //Aggiungi tutto al PannelloNord---NORTH
        panelNorth.add(panelStadioN);
        panelNorth.add(totaleN);
        panelNorth.add(buttonN);
        panelNorth.setBorder(new TitledBorder(new EtchedBorder(),"Calcola incasso totale"));
         
        //panelStadio ---CENTER
                JPanel panelStadioC=new JPanel();
                stadi1=new JComboBox();
                    for(Stadio elements:arrayStadi){
                        stadi1.addItem(elements);
                    }
                   
                panelStadioC.add(stadi1);
                 
         /*
        //Radio button--CENTER-SOUTH 
        JPanel sottraiAggiungiC=new JPanel();
        final JRadioButton sottraiPrezzoC=new JRadioButton("Sottrai");
        final JRadioButton aggiungiPrezzoC=new JRadioButton("Aggiungi");
        ButtonGroup tastieraC=new ButtonGroup();
        tastieraC.add(aggiungiPrezzoC);
        tastieraC.add(sottraiPrezzoC);
        sottraiAggiungiC.add(aggiungiPrezzoC);
        sottraiAggiungiC.add(sottraiPrezzoC);
        sottraiAggiungiC.setBorder(new TitledBorder(new EtchedBorder(),"Scegli Operazione"));
         
         */
         
        //JTextField --CENTER-SOUTH
        JPanel textAreaC=new JPanel();
        final JTextField textAreaC1=new JTextField(10);
        textAreaC.add(textAreaC1);
         
        //Button ---Center-SOUTH
        JPanel buttonC=new JPanel();
        final JButton buttonC1=new JButton("Apporta Modifiche");
        buttonC.add(buttonC1);
         
        //aggiungi al pannello Center
        panelCenter.add(new JPanel());
        panelCenter.add(panelStadioC);
        panelCenter.add(new JPanel());
        //panelCenter.add(sottraiAggiungiC);
        panelCenter.add(textAreaC);
        panelCenter.add(new JPanel());
        panelCenter.add(buttonC);
        panelCenter.setBorder(new TitledBorder(new EtchedBorder(),"Modifica prezzo degli stadi"));
         
         
        //panelStadio ---CENTER
        JPanel panelStadioS=new JPanel();
        stadi2=new JComboBox();
            for(Stadio elements:arrayStadi)
            {
                stadi2.addItem(elements);
            }
        panelStadioS.add(stadi2);
             
                 
        //Radio button--SOUTH-SOUTH
            JPanel sottraiAggiungiS=new JPanel();
            final JRadioButton sottraiCapienzaS=new JRadioButton("Sottrai");
            final JRadioButton aggiungiCapienzaS=new JRadioButton("Aggiungi");
            ButtonGroup tastieraS=new ButtonGroup();
            tastieraS.add(aggiungiCapienzaS);
            tastieraS.add(sottraiCapienzaS);
            sottraiAggiungiS.add(aggiungiCapienzaS);
            sottraiAggiungiS.add(sottraiCapienzaS);
            sottraiAggiungiS.setBorder(new TitledBorder(new EtchedBorder(),"Scegli Operazione"));
                 
            //JTextField --SOUTH-SOUTH
            JPanel textAreaS=new JPanel();
            final JTextField textAreaS1=new JTextField(10);
            textAreaS.add(textAreaS1);
                 
            //Button ---SOUTH-SOUTH
            JPanel buttonS=new JPanel();
            final JButton buttonS1=new JButton("Apporta Modifiche");
            buttonS.add(buttonS1);
             
            //Aggiungi al pannello South
            panelSouth.add(new JPanel());
            panelSouth.add(panelStadioS);
            panelSouth.add(new JPanel());
            panelSouth.add(sottraiAggiungiS);
            panelSouth.add(textAreaS);
            panelSouth.add(buttonS);
            panelSouth.setBorder(new TitledBorder(new EtchedBorder(),"Modifica capienza degli stadi"));
             
             
            //Inizzializzazione ActionListener
            class ActionMenagment implements ActionListener
            {
                public void actionPerformed(ActionEvent e) 
                {
                    JButton button=(JButton)e.getSource();
                            if(button.equals(buttonN1))
                            {
                                double totale=elencoBiglietti.calcolaTotale((String)stadi.getSelectedItem());
                                totale1.setText("Totale incassi:  "+totale);
                            }else if(button.equals(buttonC1))
                            {
                                float quantita=Float.parseFloat(textAreaC1.getText());
                                ArrayList<Partita> partite=elencoPartite.getPartite();
                                
                                for(Partita elements:partite)
                                {
                                    if(((Stadio)stadi1.getSelectedItem()).getNome().equals(elements.getStadio().getNome()))
                                    {
                                        elements.setCostoBiglietto(quantita);
                                    }
                                }
                        }else
                        {
                        	try
                        	{
                        		int quantita=Integer.parseInt(textAreaS1.getText());
                                Stadio stadio=(Stadio) stadi2.getSelectedItem();
                                if(sottraiCapienzaS.isSelected()==true){
                                    stadio.modificaCapienza(-quantita);
                                }
                                else{
                                    stadio.modificaCapienza(quantita);
                                }
                        		
                        	}catch(CapacitaException e1)
                        	{
                        		JOptionPane pane=new JOptionPane();
                        		pane.showMessageDialog(null, e1.getMessage());
                        	}
                            
                        }
                     
                }
            }
            
            buttonN1.addActionListener(new ActionMenagment());
            buttonC1.addActionListener(new ActionMenagment());
            buttonS1.addActionListener(new ActionMenagment());
            
            //Aggiungi al pannelloFinale
        panel.add(panelNorth,BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelSouth, BorderLayout.SOUTH);
         
        return panel;
	}

	private JPanel createInserisciPartitaPanel() 
	{
		JPanel pannello=new JPanel();
		pannello.setLayout(new GridLayout(5,1));
		
		pannello.add(createSquadrePanel());
		pannello.add(createStadioPartitaPanel());
		pannello.add(createDataPanel());
		pannello.add(createPrezzoPanel());
		pannello.add(createButtonPanel());
		
		return pannello;
	}
	
	private JPanel createSquadrePanel()
	{
		JPanel pannello=new JPanel();
		
		squadraA=new JTextField(5);
		squadraB=new JTextField(5);
		
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci squadra Casa e Trasferta"));
		
		pannello.add(squadraA);
		pannello.add(squadraB);
		
		return pannello;
	}
	
	private JPanel createDataPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci Data Partita"));
		
		anno = new JComboBox();
		
		data=new GregorianCalendar();
		int annoAttuale=data.get(GregorianCalendar.YEAR);
		
		for(int i=annoAttuale;i<=annoAttuale+5;i++)
		{
			anno.addItem(i);
		}
		
		
		mese = new JComboBox();
		
		for(int i=0;i<12;i++)
		{
			mese.addItem(mesi[i]);
		}
		
		giorno = new JComboBox();
		
		ora=new JComboBox();
		
		for(int i=1;i<=24;i++)
		{
			ora.addItem(""+i);
		}
		
		minuto=new JComboBox();
		
		for(int i=0;i<=59;i++)
		{
			minuto.addItem(""+i);
		}
		
		class GiorniListener implements ActionListener
		{

			int i;
			
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
		
		panel.add(anno);
		panel.add(mese);
		panel.add(giorno);
		panel.add(new JLabel("Orario: "));
		panel.add(ora);
		panel.add(minuto);
		
		return panel;
	}
	
	private JPanel createPrezzoPanel()
	{
		JPanel pannello=new JPanel();
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci Prezzo Partita"));
		
		prezzo=new JTextField(5);
		
		pannello.add(prezzo);
		
		return pannello;
		
	}
	
	private JPanel createButtonPanel()
	{
		JPanel pannello=new JPanel();
		JButton button=new JButton("INSERISCI");
		
		pannello.add(button);
		
		class InserisciListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				String squadraCasa=squadraA.getText();
				String squadraTrasferta=squadraB.getText();
				int giornoB=Integer.parseInt((String)giorno.getSelectedItem());
				int meseB=(int)mese.getSelectedIndex();
				int annoB=(int)anno.getSelectedItem();
				int oraB=Integer.parseInt((String)ora.getSelectedItem());
				int minutoB=Integer.parseInt((String)minuto.getSelectedItem());
				Stadio s=(Stadio)stadio.getSelectedItem();
				
				GregorianCalendar dataPartita=new GregorianCalendar();
				dataPartita.set(annoB, meseB, giornoB,oraB,minutoB);
				
				float prezzoPartita=Float.parseFloat(prezzo.getText());
				
				Partita p=new Partita(squadraCasa,squadraTrasferta,dataPartita,s,prezzoPartita);
				elencoPartite.addPartita(p);
				
				thisFrame.dispose();
				framePadre.setEnabled(true);
			}
		}
		
		button.addActionListener(new InserisciListener());
		
		return pannello;
	}
	
	private JPanel createStadioPartitaPanel()
	{
		JPanel pannello=new JPanel();
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci Stadio Partita"));
		
		stadio=new JComboBox();
		
		for(Stadio s:elencoStadi.getStadi())
		{
			stadio.addItem(s);
		}
		
		pannello.add(stadio);
		
		return pannello;
		
	}

}
