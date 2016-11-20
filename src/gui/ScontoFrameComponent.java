package gui;

import progettocdr.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ScontoFrameComponent extends JFrame
{
	private Partita partita;
	private JFrame thisFrame=this,framePadre;
	private final int FRAME_WIDTH=500,FRAME_HEIGHT=500;
	
	public ScontoFrameComponent(JFrame framePadre,Partita p)
	{
		this.partita=p;
		this.framePadre=framePadre;
		
		JLabel label=new JLabel();
		label.setText("ATTIVA POLITICHE DI SCONTO");
		label.setAlignmentX(CENTER_ALIGNMENT);
		thisFrame.add(label,BorderLayout.NORTH);
		
		thisFrame.add(createCheckPanel());
		
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

	private JPanel createCheckPanel() 
	{
		JPanel pannello=new JPanel();
		
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"seleziona politica"));
		
		JCheckBox sconto1=new JCheckBox("Fascia di età");
		JCheckBox sconto2=new JCheckBox("Fascia di oraria");
		JCheckBox sconto3=new JCheckBox("Infrasettimanale");
		
		if(partita.getSconto(0))
			sconto1.setSelected(true);
		if(partita.getSconto(1))
			sconto2.setSelected(true);
		if(partita.getSconto(2))
			sconto3.setSelected(true);
		
		
		pannello.add(sconto1);
		pannello.add(sconto2);
		pannello.add(sconto3);
		
		JButton button=new JButton("Attiva/Disattiva");
		pannello.add(button);
		
		class CheckListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(sconto1.isSelected())
				{
					partita.attivaDisattivaScontoPartita(0,true);
				}
				else
					partita.attivaDisattivaScontoPartita(0, false);
				
				if(sconto2.isSelected())
				{
					partita.attivaDisattivaScontoPartita(1,true);
				}
				else
					partita.attivaDisattivaScontoPartita(1, false);
				
				if(sconto3.isSelected())
				{
					partita.attivaDisattivaScontoPartita(2,true);
				}
				else
					partita.attivaDisattivaScontoPartita(2, false);
				
				framePadre.setEnabled(true);
				thisFrame.dispose();
			}
		}
		
		button.addActionListener(new CheckListener());
		
		return pannello;
		
	}

}
