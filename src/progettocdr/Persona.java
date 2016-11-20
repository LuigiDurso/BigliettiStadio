package progettocdr;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Persona implements Serializable
{
	private String nome,cognome;
	private GregorianCalendar dataNascita;
	private String login,password;
	
	public Persona(String nome,String cognome, GregorianCalendar dataNascita,String login,String password)
	{
		this.nome=nome;
		this.cognome=cognome;
		this.dataNascita=dataNascita;
		this.login=login;
		this.password=password;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	
	public String getLogin()
	{
		return login;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public GregorianCalendar getNascitaUtente() 
	{
		return dataNascita;
	}
	
	public boolean verificaPassword() throws PasswordException
	{
		char c;
		int countLettere=0,countNumeri=0;
		
		for(int i=0;i<password.length();i++)
		{
			c=password.charAt(i);
			if(Character.isDigit(c))
			{
				countNumeri++;
			}
			if( Character.isLetter(c))
			{
				countLettere++;
			}
		}
		if(countLettere<=0 || countNumeri<=0)
		{
			throw new PasswordException();
		}
			
		return countLettere>0 && countNumeri>0;
	}
	
	public boolean corrispondenzaPassword(String psw) throws PasswordNonCorrispondenteException
	{
		if(password.equals(psw))
			return true;
		else
			throw new PasswordNonCorrispondenteException();
	}


}
