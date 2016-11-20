package progettocdr;

import java.io.Serializable;

public class Posto implements Serializable
{
	private int numeroPosto,status;
	
	public Posto(int numeroPosto,int status)
	{
		this.numeroPosto=numeroPosto;
		this.status=status;
		
	}
	
	public int getNumPosto()
	{
		return numeroPosto;
	}
	
	public void setStatus(int i)
	{
		status=i;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public boolean getDisponibilita() throws PostoNonDisponibileException
	{
		if(status>0)
			return true;
		else
			throw new PostoNonDisponibileException();
	}

}
