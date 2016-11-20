package gui;
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
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import progettocdr.*;

public class AcquistaPrenotazioneComponent extends JFrame
{
	private JFrame framePadre,thisFrame=this;
	private Biglietto biglietto;
	private Biglietti elencoBiglietti;
	private final int FRAME_WIDTH=700,FRAME_HEIGHT=200;
	
	public AcquistaPrenotazioneComponent(JFrame framePadre,Biglietto b,Biglietti elencoBiglietti)
	{
		this.elencoBiglietti=elencoBiglietti;
		this.framePadre=framePadre;
		this.biglietto=b;
		
		thisFrame.add(createCenterPanel(),BorderLayout.CENTER);
		thisFrame.add(createSouthPanel(),BorderLayout.SOUTH);
		
		thisFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		class WindowsClose implements WindowListener
		{
			
			public void windowOpened(WindowEvent e) {}
			
			public void windowClosing(WindowEvent e) 
			{
				((FrameComponentCliente) framePadre).caricamentoAcquisti();
				((FrameComponentCliente) framePadre).caricamentoPrenotazioni();
				
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
		JButton cancella=new JButton("CANCELLA PRENOTAZIONE");
		JButton annulla=new JButton("ANNULLA");
		
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(1,3));
		
		pannelloInterno.add(acquista);
		pannelloInterno.add(cancella);
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
					((FrameComponentCliente) framePadre).caricamentoAcquisti();
					((FrameComponentCliente) framePadre).caricamentoPrenotazioni();
					framePadre.setEnabled(true);
					thisFrame.dispose();
				}
				if(button.getText().equals("CANCELLA PRENOTAZIONE"))
				{
					biglietto.cancellaPrenotazione();
					((FrameComponentCliente) framePadre).caricamentoAcquisti();
					((FrameComponentCliente) framePadre).caricamentoPrenotazioni();
					framePadre.setEnabled(true);
					thisFrame.dispose();
				}
				if(button.getText().equals("ANNULLA"))
				{
					
					framePadre.setEnabled(true);
					thisFrame.dispose();
				}
			}
		}
		
		acquista.addActionListener(new ButtonListener());
		cancella.addActionListener(new ButtonListener());
		annulla.addActionListener(new ButtonListener());
		
		pannello.add(pannelloInterno);
		
		return pannello;
		
	}


}
