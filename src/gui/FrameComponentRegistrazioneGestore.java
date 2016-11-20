package gui;
import progettocdr.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class FrameComponentRegistrazioneGestore extends JFrame 
{

	private JFrame thisFrame = this;
	private JFrame framePadre;
	private JTextField nomet;
	private JTextField cognomet;
	private String[] mesi={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	private Integer[] giorni = {31,28,31,30,31,30,31,31,30,31,30,31};
	private JButton conferma;
	private int indice;
	private GregorianCalendar data;
	private final int FRAME_WIDTH=400,FRAME_HEIGHT=300;
	private Persone elencoPersone;
	private JComboBox anno,mese,giorno;

	public FrameComponentRegistrazioneGestore(JFrame framePadre,Persone elencoPersone)
	{
		this.framePadre = framePadre;
		this.elencoPersone=elencoPersone;
		
		JLabel label = new JLabel("PRIMA REGISTRAZIONE GESTORE");
		
		JPanel panel2 = new JPanel();
		panel2.add(label);
		thisFrame.add(panel2,BorderLayout.NORTH);

		JPanel center = createCenter();
		this.add(center);

		thisFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		class WindowsClose implements WindowListener
		{
			
			public void windowOpened(WindowEvent e) {}
			
			public void windowClosing(WindowEvent e) 
			{
				framePadre.setEnabled(true);
				thisFrame.dispose();
			}
			
			public void windowClosed(WindowEvent e) {	}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			
		}
		
		thisFrame.addWindowListener(new WindowsClose());
	}

	private JPanel createButton() 
	{
		JPanel panel = new JPanel();
		conferma = new JButton("Conferma");

		class ConfermaButtonListener implements ActionListener
		{


			public void actionPerformed(ActionEvent e) 
			{
				
				String nome = nomet.getText();
				String cognome = cognomet.getText();
				int giornoB=Integer.parseInt((String)giorno.getSelectedItem());
				int meseB=(int)mese.getSelectedIndex()+1;
				int annoB=(int)anno.getSelectedItem();
				
				GregorianCalendar dataNascita=new GregorianCalendar();
				dataNascita.set(annoB, meseB, giornoB);
				
				Gestore gestore=new Gestore(nome,cognome,dataNascita,"admin","admin");
				elencoPersone.addPersona(gestore);
				
				framePadre.setEnabled(true);
				thisFrame.dispose();

			}

		}

		conferma.addActionListener( new ConfermaButtonListener());
		panel.add(conferma,BorderLayout.CENTER);

		return panel;
	}

	public JPanel createCenter() 
	{

		JPanel credenziali = new JPanel();
		credenziali.setLayout(new GridLayout(5,1));
		
		nomet = new JTextField("Nome");
		cognomet = new JTextField("Cognome");
		
		credenziali.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci_credenziali"));
		
		credenziali.add(nomet);
		credenziali.add(cognomet);
		credenziali.add(createRadioButton());
		credenziali.add(createDate());
		credenziali.add(createButton());

		class ClearListener implements MouseListener
		{


			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			
			public void mousePressed(MouseEvent arg0) 
			{

				JTextField text = (JTextField)arg0.getSource();
				text.setText("");
			}
			
			public void mouseReleased(MouseEvent arg0) {}

		}
		nomet.addMouseListener(new ClearListener());
		cognomet.addMouseListener(new ClearListener());
		
		return credenziali;
	}

	private JPanel createDate() 
	{

		JPanel panel = new JPanel();
		
		anno = new JComboBox();
		
		data=new GregorianCalendar();
		int annoAttuale=data.get(GregorianCalendar.YEAR);
		
		for(int i=1950;i<=annoAttuale;i++)
		{
			anno.addItem(i);
		}
		
		
		mese = new JComboBox();
		
		for(int i=0;i<12;i++)
		{
			mese.addItem(mesi[i]);
		}
		
		giorno = new JComboBox();
		
		
		
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
		
		return panel;
	}

	private JPanel createRadioButton() 
	{

		JPanel panel = new JPanel();
		
		JRadioButton maschio = new JRadioButton("Maschio");
		JRadioButton femmina = new JRadioButton("Femmina");
		
		ButtonGroup group = new ButtonGroup();
		group.add(maschio);
		group.add(femmina);
		
		panel.add(maschio);
		panel.add(femmina);
		
		return panel;
	}
}
