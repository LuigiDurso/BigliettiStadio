package gui;
import progettocdr.*;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RegistrazioneCliente extends JFrame 
{

	private String[] mesi ={"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	private Integer[] numeroGiorni={31,28,31,30,31,30,31,31,30,31,30,31};
	private JPasswordField pass1;
	private JPasswordField re_pass1;
	private JFrame thisFrame=this,framePadre;
	private GregorianCalendar data;
	private final int FRAME_WIDTH=360,FRAME_HEIGHT=400;
	private Persone elencoPersone;
	private JComboBox giorno,mese,anno;
	private JTextField email1,nome1,cognome1;


	public RegistrazioneCliente(JFrame framePadre,Persone elencoPersone)
	{
		this.framePadre=framePadre;
		this.elencoPersone=elencoPersone;
		
		JLabel Benvenuto=new JLabel("Benveuti nel sistema C.D.R");
		Font f=new Font("Helvetica",Font.BOLD,16);
		Benvenuto.setFont(f);

		thisFrame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		thisFrame.setLayout(new GridLayout(2,1));


		JPanel nord=new JPanel();
		nord.add(Benvenuto,BorderLayout.NORTH);
		nord.add(setNamePass(), BorderLayout.CENTER);
		thisFrame.add(nord);
		
		JPanel south =new JPanel();
		south.add(setBirthday(),BorderLayout.NORTH);
		south.add(setSessoUtente(), BorderLayout.CENTER);
		south.add(setButton(),BorderLayout.SOUTH);

		thisFrame.add(south);
		
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

	/**
	 * Settiamo i text field del nome del cognome ,pass e reinserimento pass
	 * @return il pannello implementatore;
	 */
	public JPanel setNamePass()
	{
		JPanel primoPannello=new JPanel();
		primoPannello.setLayout(new GridLayout(4,1));

		JPanel name=new JPanel();
		
		nome1=new JTextField(10);
		nome1.setText("First-Name");
		
		cognome1=new JTextField(10);
		cognome1.setText("Surname");
		
		name.add(nome1);
		name.add(cognome1);

		//email
		JPanel email=new JPanel();
		email1=new JTextField(12);
		email1.setText("Email or nickname");
		email.add(email1);

		//password
		JPanel pass=new JPanel();
		pass1=new JPasswordField(12);
		pass1.setText("Enter Password");
		pass.add(pass1);
		
		//re-inserimeto
		JPanel re_pass=new JPanel();
		re_pass1=new JPasswordField(12);
		re_pass1.setText("Re-enter Password");
		re_pass.add(re_pass1);

		class FieldListener implements MouseListener
		{
			
			public void mouseClicked(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) 
			{
				JTextField field=(JTextField) e.getSource();
				field.setText("");
			}
			
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		}


		nome1.addMouseListener(new FieldListener());
		cognome1.addMouseListener(new FieldListener());
		email1.addMouseListener(new FieldListener());
		pass1.addMouseListener(new FieldListener());
		re_pass1.addMouseListener(new FieldListener());

		primoPannello.add(name);
		primoPannello.add(email);
		primoPannello.add(pass);
		primoPannello.add(re_pass);

		return primoPannello;

	}
	public JPanel setBirthday()
	{	
		JPanel scrittaCompleanno=new JPanel();
		scrittaCompleanno.setBorder(new TitledBorder(new EtchedBorder(),"Data di Nascita"));


		JLabel scritta1=new JLabel("Birthday:   ");
		scrittaCompleanno.add(scritta1,BorderLayout.WEST);

		JPanel pannello=new JPanel();
		giorno=new JComboBox();
		mese=new JComboBox();
		anno=new JComboBox();

		pannello.add(anno);
		pannello.add(mese);
		pannello.add(giorno);


		class DataEvent implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				int indice=0;
				
				giorno.removeAllItems();
				
				String meseScelto=(String)mese.getSelectedItem();

				for(int i=0;i<12;i++)
				{
					if(mesi[i].equals(meseScelto))
					{
						indice=i;
					}
				}
				for(int i=1;i<=numeroGiorni[indice];i++)
				{
					giorno.addItem(""+i);
				}

				if(indice==1 && ((int)anno.getSelectedItem()%4)==0 || ((int)anno.getSelectedItem()%400)==0)
				{
					giorno.addItem(""+29);
				}
			}
		}




		for(int i=0;i<12;i++)
		{
			mese.addItem(mesi[i]);
		}
		
		data=new GregorianCalendar();
		int annoAttuale=data.get(GregorianCalendar.YEAR);


		for(int i=1920;i<=annoAttuale;i++){
			anno.addItem(i);
		}

		mese.addActionListener(new DataEvent());
		anno.addActionListener(new DataEvent());
		
		scrittaCompleanno.add(pannello,BorderLayout.EAST);

		return scrittaCompleanno;
	}
	
	public JPanel setSessoUtente()
	{
		JPanel sesso=new JPanel();
		sesso.setBorder(new TitledBorder(new EtchedBorder(),"Sesso"));

		JRadioButton maschio=new JRadioButton("Maschio");
		JRadioButton femmina=new JRadioButton("Femmina");

		ButtonGroup group=new ButtonGroup();
		group.add(maschio);
		group.add(femmina);

		sesso.add(maschio);
		sesso.add(femmina);

		return sesso;
	}


	public JPanel setButton()
	{
		JPanel buttonpiu=new JPanel();
		JButton button=new JButton("Registrati!");
		
		class RegistrazioneUtente implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{ 
				try
				{
					String nome=nome1.getText();
					String cognome=cognome1.getText();
					String login=email1.getText();
					
					elencoPersone.searchLogin(login);
					
					String password=pass1.getText();
					
					int giornoB=Integer.parseInt((String)giorno.getSelectedItem());
					int meseB=(int)mese.getSelectedIndex()+1;
					int annoB=(int)anno.getSelectedItem();
					
					GregorianCalendar dataNascita=new GregorianCalendar();
					dataNascita.set(annoB, meseB, giornoB);
					
					Cliente c=new Cliente(nome,cognome,dataNascita,login,password);
					c.verificaPassword();
					c.corrispondenzaPassword(re_pass1.getText());
					elencoPersone.addPersona(c);
					
					framePadre.setEnabled(true);
					thisFrame.dispose();
					
				}catch(PasswordException e2)
				{
					JOptionPane pane=new JOptionPane();
					pane.showMessageDialog(null, e2.getMessage());
				}catch (LoginPresenteException e1) 
				{
					JOptionPane pane=new JOptionPane();
					pane.showMessageDialog(null, e1.getMessage());
				}catch(PasswordNonCorrispondenteException e3)
				{
					JOptionPane pane=new JOptionPane();
					pane.showMessageDialog(null, e3.getMessage());
				}
				
			}
		}
		
		button.addActionListener(new RegistrazioneUtente());
		
		buttonpiu.add(button,BorderLayout.WEST);

		return buttonpiu;
	}
}
