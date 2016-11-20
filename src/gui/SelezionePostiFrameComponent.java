package gui;
import progettocdr.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class SelezionePostiFrameComponent extends JFrame
{
	private JFrame thisFrame=this;
	private JFrame framePadre;
	private Cliente cliente;
	private Partita partita;
	private Biglietti elencoBiglietti;
	private int indice,capienzaParziale,capienzaResidua;
	private ActionListener l;
	private final int FRAME_WIDTH=1050,FRAME_HEIGHT=550;
	
	
	public SelezionePostiFrameComponent(JFrame framePadre,Cliente cliente,Partita p,Biglietti elencoBiglietti)
	{
		this.framePadre=framePadre;
		this.cliente=cliente;
		this.partita=p;
		this.elencoBiglietti=elencoBiglietti;
		indice=1;
		capienzaParziale=p.getPosti().size()/4;
		capienzaResidua=p.getPosti().size()%4;
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				JButton b=(JButton) e.getSource();
				int i=Integer.parseInt(b.getText());
				
				Posto p=partita.getPosto(i);
				
				try
				{
					if(p.getDisponibilita())
					{
						Biglietto biglietto=new Biglietto(cliente,partita,partita.getCostoBiglietto(),p);
						biglietto.setPrezzoBiglietto();
						
						BigliettoComponent frame=new BigliettoComponent(thisFrame,biglietto,framePadre,elencoBiglietti);
						frame.setVisible(true);
						thisFrame.setEnabled(false);
					}
					
				}catch(PostoNonDisponibileException e1)
				{
					JOptionPane pane=new JOptionPane();
					pane.showMessageDialog(null, "Posto selezionato non disponibile!!!");
				}
			}
		}
		
		l=new ButtonListener();
		
		
		//classe interna per l'immagine dello stadio
		class ImmagineJPanel extends JPanel
		{
			private Image immagine;
			
			public ImmagineJPanel()
			{
				immagine=Toolkit.getDefaultToolkit().createImage("./campoCalcio.jpg");
				try
				{
					MediaTracker track=new MediaTracker(this);
					track.addImage(immagine, 0);
					track.waitForID(0);
				}catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
			
			protected void paintComponent(Graphics g)
			{
				this.setOpaque(false);
				g.drawImage(immagine, this.getWidth()-500,this.getHeight()-300, null);
				super.paintComponent(g);
			}	
		}
		
		ImmagineJPanel pannello=new ImmagineJPanel();
		thisFrame.add(pannello, BorderLayout.CENTER);
		
		thisFrame.add(createNorthPanel(),BorderLayout.NORTH);
		thisFrame.add(createSouthPanel(),BorderLayout.SOUTH);
		thisFrame.add(createWestPanel(),BorderLayout.WEST);
		thisFrame.add(createEastPanel(), BorderLayout.EAST);
		
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
	
	
	private JPanel createNorthPanel()
	{
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(3,8));
		
		for(indice=indice;indice<=capienzaParziale;indice++)
		{
			JButton button=new JButton(""+indice);
			button.addActionListener(l);
			Posto p=partita.getPosto(indice);
			if(p!=null)
			{
				if(p.getStatus()==1)
				{
					button.setBackground(Color.ORANGE);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
				if(p.getStatus()==0)
				{
					try
					{
						if(partita.controlloOrarioPartita())
						{
							button.setBackground(Color.DARK_GRAY);
							button.setOpaque(true);
							button.setBorderPainted(false);
							
						}

					}catch(PartitaNonPrenotabileException ex)
					{
						p.setStatus(1);
						button.setBackground(Color.ORANGE);
						button.setOpaque(true);
						button.setBorderPainted(false);	
						
					}
					
				}
				if(p.getStatus()==-1)
				{
					button.setBackground(Color.RED);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
			}
			pannelloInterno.add(button);
		}
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Tribuna B"));
		pannello.add(pannelloInterno,BorderLayout.CENTER);
		
		return pannello;
	}
	
	private JPanel createSouthPanel()
	{
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(3,8));
		
		for(indice=indice;indice<=capienzaParziale*2;indice++)
		{
			JButton button=new JButton(""+indice);
			button.addActionListener(l);
			Posto p=partita.getPosto(indice);
			if(p!=null)
			{
				if(p.getStatus()==1)
				{
					button.setBackground(Color.ORANGE);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
				if(p.getStatus()==0)
				{
					try
					{
						if(partita.controlloOrarioPartita())
						{
							button.setBackground(Color.DARK_GRAY);
							button.setOpaque(true);
							button.setBorderPainted(false);
							
						}

					}catch(PartitaNonPrenotabileException ex)
					{
						p.setStatus(1);
						button.setBackground(Color.ORANGE);
						button.setOpaque(true);
						button.setBorderPainted(false);	
						
					}
				}
				if(p.getStatus()==-1)
				{
					button.setBackground(Color.RED);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
			}
			pannelloInterno.add(button);
		}
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Tribuna A"));
		pannello.add(pannelloInterno,BorderLayout.CENTER);
		
		return pannello;
	}
	
	private JPanel createWestPanel()
	{
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(8,3));
		
		for(indice=indice;indice<=capienzaParziale*3;indice++)
		{
			JButton button=new JButton(""+indice);
			button.addActionListener(l);
			Posto p=partita.getPosto(indice);
			if(p!=null)
			{
				if(p.getStatus()==1)
				{
					button.setBackground(Color.GREEN);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
				if(p.getStatus()==0)
				{
					try
					{
						if(partita.controlloOrarioPartita())
						{
							button.setBackground(Color.DARK_GRAY);
							button.setOpaque(true);
							button.setBorderPainted(false);
							
						}

					}catch(PartitaNonPrenotabileException ex)
					{
						p.setStatus(1);
						button.setBackground(Color.ORANGE);
						button.setOpaque(true);
						button.setBorderPainted(false);	
						
					}
				}
				if(p.getStatus()==-1)
				{
					button.setBackground(Color.RED);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
			}
			pannelloInterno.add(button);
		}
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Curva Nord"));
		pannello.add(pannelloInterno,BorderLayout.CENTER);
		
		return pannello;
	}
	
	private JPanel createEastPanel()
	{
		JPanel pannello=new JPanel();
		
		JPanel pannelloInterno=new JPanel();
		pannelloInterno.setLayout(new GridLayout(8,3));
		
		for(indice=indice;indice<=(capienzaParziale*4)+capienzaResidua;indice++)
		{
			JButton button=new JButton(""+indice);
			button.addActionListener(l);
			Posto p=partita.getPosto(indice);
			if(p!=null)
			{
				if(p.getStatus()==1)
				{
					button.setBackground(Color.GREEN);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
				if(p.getStatus()==0)
				{
					try
					{
						if(partita.controlloOrarioPartita())
						{
							button.setBackground(Color.DARK_GRAY);
							button.setOpaque(true);
							button.setBorderPainted(false);
							
						}

					}catch(PartitaNonPrenotabileException ex)
					{
						p.setStatus(1);
						button.setBackground(Color.ORANGE);
						button.setOpaque(true);
						button.setBorderPainted(false);	
						
					}
					
				}
				if(p.getStatus()==-1)
				{
					button.setBackground(Color.RED);
					button.setOpaque(true);
					button.setBorderPainted(false);
				}
			}
			pannelloInterno.add(button);
		}
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Curva Sud"));
		pannello.add(pannelloInterno,BorderLayout.CENTER);
		
		return pannello;
	}
}

