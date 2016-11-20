package progettocdr;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Gestore extends Persona implements Serializable
{
	public Gestore(String nome,String cognome,GregorianCalendar dataNascita,String login,String password)
	{
		super(nome,cognome,dataNascita,login,password);
	}

}
