package gui;
import progettocdr.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class BigliettoComponent extends JFrame
{
	private JFrame framePadre,thisFrame=this;
	private Biglietto biglietto;
	private JFrame frameNonno;
	private Biglietti elencoBiglietti;
	private final int FRAME_WIDTH=500,FRAME_HEIGHT=200;
	
	public BigliettoComponent(JFrame framePadre,Biglietto biglietto,JFrame frameNonno,Biglietti elencoBiglietti)
	{
		this.elencoBiglietti=elencoBiglietti;
		this.frameNonno=frameNonno;
		this.framePadre=framePadre;
		this.biglietto=biglietto;
		
		thisFrame.add(createCenterPanel(),BorderLayout.CENTER);
		thisFrame.add(createSouthPanel(),BorderLayout.SOUTH);
		
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
	
	public JPanel createCenterPanel()
	{
		JPanel pannello=new JPanel();
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Dati Biglietti"));
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(3,1));
		
		String squadraCasa=biglietto.getPartita().getSquadraCasa();
		String squadraTrasferta=biglietto.getPartita().getSquadraTrasferta();
		
		
		pannelloInterno.add(new JLabel(""+squadraCasa+"-"+squadraTrasferta));
		
		int giorno=biglietto.getPartita().getData().get(GregorianCalendar.DAY_OF_MONTH);
		int mese=biglietto.getPartita().getData().get(GregorianCalendar.MONTH)+1;
		int anno=biglietto.getPartita().getData().get(GregorianCalendar.YEAR);
		
		pannelloInterno.add(new JLabel("Presso lo stadio: "+biglietto.getStadio()+" In Data: "+giorno+"/"+mese+"/"+anno));
		
		pannelloInterno.add(new JLabel("Posto numero: "+biglietto.getNumeroPosto()+" al Prezzo di € "+biglietto.getPrezzoBiglietto()));
		
		pannello.add(pannelloInterno);
		
		return pannello;
	}
	
	public JPanel createSouthPanel()
	{
		JButton acquista=new JButton("ACQUISTA");
		JButton prenota=new JButton("PRENOTA");
		JButton annulla=new JButton("ANNULLA");
		
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(1,3));
		
		pannelloInterno.add(acquista);
		pannelloInterno.add(prenota);
		pannelloInterno.add(annulla);
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e1)
			{
				JButton button=(JButton)e1.getSource();
				if(button.getText().equals("ACQUISTA"))
				{
					biglietto.acquistaBiglietto();
					elencoBiglietti.aggiungiBiglietti(biglietto);
					((FrameComponentCliente) frameNonno).caricamentoAcquisti();
					((FrameComponentCliente) frameNonno).caricamentoPrenotazioni();
					frameNonno.setEnabled(true);
					framePadre.dispose();
					thisFrame.dispose();
					
				}
				if(button.getText().equals("PRENOTA"))
				{
					try
					{
						if(biglietto.getPartita().controlloOrarioPartita())
						{
							biglietto.prenotaBiglietto();
							((FrameComponentCliente) frameNonno).caricamentoAcquisti();
							((FrameComponentCliente) frameNonno).caricamentoPrenotazioni();
							frameNonno.setEnabled(true);
							framePadre.dispose();
							thisFrame.dispose();
						}
						
					}catch(PartitaNonPrenotabileException ex1)
					{
						JOptionPane pane=new JOptionPane();
						pane.showMessageDialog(null, ex1.getMessage());
					}
					
				}
				if(button.getText().equals("ANNULLA"))
				{
					
					framePadre.setEnabled(true);
					thisFrame.dispose();
				}
			}
		}
		
		acquista.addActionListener(new ButtonListener());
		prenota.addActionListener(new ButtonListener());
		annulla.addActionListener(new ButtonListener());
		
		pannello.add(pannelloInterno);
		
		return pannello;
		
	}

}
