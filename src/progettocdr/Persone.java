package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;

public class Persone implements Serializable
{
	private ArrayList<Persona> persone;
	
	public Persone()
	{
		persone=new ArrayList<>();
	}
	
	public ArrayList<Persona> getPersone()
	{
		return persone;
	}
	
	public void addPersona(Persona p)
	{
		persone.add(p);
	}
	
	public Persona searchPersona(String login,String password) throws PersonaNonPresenteException
	{
		for(Persona p:persone)
		{
			if(p.getLogin().equals(login) && p.getPassword().equals(password))
			{
				return p;
			}
		}
		if(login.equals("admin") && password.equals("admin"))
		{
			return null;
		}
		throw new PersonaNonPresenteException();
		
	}
	
	public boolean searchLogin(String login) throws LoginPresenteException
	{
		for(Persona p:persone)
		{
			if(p.getLogin().equals(login))
			{
				throw new LoginPresenteException();
			}
		}
		return true;
		
	}

}
