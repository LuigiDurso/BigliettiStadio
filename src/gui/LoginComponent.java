package gui;
import progettocdr.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class LoginComponent extends JFrame
{
	private Persone elencoPersone;
	private Stadi elencoStadi;
	private Biglietti elencoBiglietti;
	private Partite elencoPartite;
	private JTextField login;
	private JPasswordField password;
	private JFrame thisFrame=this;
	private File filePersone,fileStadi,fileBiglietti,filePartite;
	private final int FRAME_WIDTH=400,FRAME_HEIGHT=150;
	
	public LoginComponent() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		filePersone=new File("filePersone");
		fileStadi=new File("fileStadi");
		fileBiglietti=new File("fileBiglietti");
		filePartite=new File("filePartite");
		
		if(filePersone.exists() && fileStadi.exists() && fileBiglietti.exists() && filePartite.exists())
		{
			this.loadFileBiglietti();
			this.loadFilePartite();
			this.loadFilePersone();
			this.loadFileStadi();	
		}
		else
		{
			Stadio sanSiro=new Stadio("San Siro",80,100);
			Stadio jStadium=new Stadio("JStadium",80,100);
			Stadio mapeiStadium=new Stadio("Mapei Stadium",80,100);
			Stadio artemioFranchi=new Stadio("Artemio Franchi",70,100);
			Stadio olimpicoRoma=new Stadio("Olimpico Roma",75,100);
			
			elencoStadi=new Stadi();
			elencoStadi.addStadio(sanSiro);
			elencoStadi.addStadio(jStadium);
			elencoStadi.addStadio(mapeiStadium);
			elencoStadi.addStadio(artemioFranchi);
			elencoStadi.addStadio(olimpicoRoma);
			
			Cliente cl1=new Cliente("luigi","durso",new GregorianCalendar(1993,11,29),"lg1","pw1");
			Cliente cl2=new Cliente("raffaele","ceruso",new GregorianCalendar(1995,11,05),"lg2","pw2");
			Cliente cl3=new Cliente("guido","rizzo",new GregorianCalendar(1995,4,10),"lg3","pw3");
			
			elencoPersone=new Persone();
			elencoPersone.addPersona(cl1);
			elencoPersone.addPersona(cl2);
			elencoPersone.addPersona(cl3);
			
			elencoBiglietti=new Biglietti();
			elencoPartite=new Partite();
			
			Partita p1=new Partita("Juventus","Inter",new GregorianCalendar(2016,4,20,20,45),jStadium,(float)20);
			Partita p2=new Partita("Ancona","Cesena",new GregorianCalendar(2016,0,21,21,10),mapeiStadium,(float)20);
			Partita p3=new Partita("Brescia","Poggibonsi",new GregorianCalendar(2017,13,10),artemioFranchi,(float)20);
			
			
			elencoPartite.addPartita(p1);
			elencoPartite.addPartita(p2);
			elencoPartite.addPartita(p3);
			
		}
		
		JMenuBar menuBar=new JMenuBar();
		thisFrame.setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createRegistrazioneMenu());
		
		thisFrame.add(createLabelPanel(),BorderLayout.NORTH);
		
		thisFrame.add(createCenterPanel(),BorderLayout.CENTER);
		
		thisFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		class WindowsClose implements WindowListener
		{
			
			public void windowOpened(WindowEvent e) {}
			
			public void windowClosing(WindowEvent e) 
			{
				
				try
				{
					saveFilePartite();
					saveFileBiglietti();
					saveFileStadi();
					saveFilePersone();
				}
				catch(IOException ex1)
				{
					ex1.printStackTrace();
				}
			}
			
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			
		}
		
		thisFrame.addWindowListener(new WindowsClose());

	}
	
	

	private JPanel createLabelPanel() 
	{
		JPanel pannello=new JPanel();
		
		JLabel label=new JLabel();
		label.setText("BENVENUTO NEL SISTEMA C.D.R.");
		
		pannello.setAlignmentX(CENTER_ALIGNMENT);
		pannello.add(label);
		return pannello;
		
	}

	private JPanel createCenterPanel() 
	{
		JPanel pannello=new JPanel();
		
		login=new JTextField(8);
		login.setText("Login...");
		password=new JPasswordField(8);
		password.setText("aaaaa");
		
		pannello.add(login);
		pannello.add(password);
		
		pannello.add(createLoginButtonComponent());
		
		pannello.setBorder(new TitledBorder(new EtchedBorder(),"Credenziali"));
		
		class FieldListener implements MouseListener
		{
			
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) 
			{
				JTextField field=(JTextField)e.getSource();
				field.setText("");
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}
		
		login.addMouseListener(new FieldListener());
		password.addMouseListener(new FieldListener());
		
		return pannello;
	}

	private JButton createLoginButtonComponent() 
	{
		JButton loginButton=new JButton("ACCEDI");
		
		class LoginButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				
				try
				{
					if(login.getText().equals("admin") && password.getText().equals("admin") )
					{
						if(elencoPersone.searchPersona("admin","admin")==null)
						{
							FrameComponentRegistrazioneGestore frame=new FrameComponentRegistrazioneGestore(thisFrame,elencoPersone);
							thisFrame.setEnabled(false);
							frame.setVisible(true);
						}
						else
						{
							Gestore g=(Gestore)elencoPersone.searchPersona("admin", "admin");
							GestoreFrameComponent frame=new GestoreFrameComponent(thisFrame,g,elencoPartite,elencoBiglietti,elencoStadi);
							thisFrame.setEnabled(false);
							frame.setVisible(true);
						}
						
					}
					else
					{
						Cliente c=(Cliente)elencoPersone.searchPersona(login.getText(), password.getText());
						
						FrameComponentCliente frame=new FrameComponentCliente(thisFrame,c,elencoPartite,elencoBiglietti,elencoPersone,elencoStadi);
						thisFrame.setEnabled(false);
						frame.setVisible(true);
						
					}
				}catch(PersonaNonPresenteException e1)
				{
					JOptionPane pane=new JOptionPane();
					pane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		}
		
		loginButton.addActionListener(new LoginButtonListener());
		
		return loginButton;
	}

	private JMenu createRegistrazioneMenu() 
	{
		JMenu menu=new JMenu("Registrazione");
		menu.add(createRegistrazioneClienteMenuItem());
		return menu;
		
	}

	private JMenuItem createRegistrazioneClienteMenuItem() 
	{
		JMenuItem cliente=new JMenuItem("Cliente");
		
		class ClienteListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				RegistrazioneCliente frame=new RegistrazioneCliente(thisFrame,elencoPersone);
				thisFrame.setEnabled(false);
				frame.setVisible(true);
			}
		}
		
		cliente.addActionListener(new ClienteListener());
		return cliente;
		
	}

	private JMenu createFileMenu() 
	{
		JMenu menu=new JMenu("File");
		menu.add(createExitMenuItem());
		return menu;
	}
	
	public JMenuItem createExitMenuItem()
	{
		JMenuItem exit=new JMenuItem("Exit");
		
		class ExitListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					saveFilePartite();
					saveFileBiglietti();
					saveFileStadi();
					saveFilePersone();
				}
				catch(IOException ex1)
				{
					ex1.printStackTrace();
				}
				System.exit(0);
			}
		}
		
		exit.addActionListener(new ExitListener());
		return exit;
	}
	
	/**
	 * caricamento dai file
	 */
	public void  loadFilePersone() throws ClassNotFoundException, IOException 
	{
		ObjectInputStream inputPersone=new ObjectInputStream(new FileInputStream(filePersone));
		
		elencoPersone=(Persone)inputPersone.readObject();
		inputPersone.close();
	}

	public void  loadFileStadi() throws ClassNotFoundException, IOException 
	{
		ObjectInputStream inputStadi=new ObjectInputStream(new FileInputStream(fileStadi));
		
		elencoStadi=(Stadi)inputStadi.readObject();
		inputStadi.close();
	}

	public void  loadFileBiglietti() throws ClassNotFoundException, IOException 
	{
		ObjectInputStream inputBiglietti=new ObjectInputStream(new FileInputStream(fileBiglietti));
		
		elencoBiglietti=(Biglietti)inputBiglietti.readObject();
		inputBiglietti.close();
	}

	public void  loadFilePartite() throws ClassNotFoundException, IOException 
	{
		ObjectInputStream inputPartite=new ObjectInputStream(new FileInputStream(filePartite));
		
		elencoPartite=(Partite)inputPartite.readObject();
		inputPartite.close();
	}
	
	
	/**
	 * scrittura su file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void  saveFilePersone() throws FileNotFoundException, IOException 
	{
		ObjectOutputStream outputPersone=new ObjectOutputStream(new FileOutputStream(filePersone));
		
		outputPersone.writeObject(elencoPersone);
		outputPersone.close();
	}

	public void saveFileStadi() throws FileNotFoundException, IOException 
	{
		ObjectOutputStream outputStadi=new ObjectOutputStream(new FileOutputStream(fileStadi));
		
		outputStadi.writeObject(elencoStadi);
		outputStadi.close();
	}

	public void  saveFileBiglietti() throws FileNotFoundException, IOException 
	{
		ObjectOutputStream outputBiglietti=new ObjectOutputStream(new FileOutputStream(fileBiglietti));
	    
		outputBiglietti.writeObject(elencoBiglietti);
		outputBiglietti.close();
	}
	
	public void  saveFilePartite() throws FileNotFoundException, IOException 
	{
		ObjectOutputStream outputPartite=new ObjectOutputStream(new FileOutputStream(filePartite));
	    
		outputPartite.writeObject(elencoPartite);
		outputPartite.close();
	}

	
}
