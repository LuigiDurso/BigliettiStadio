package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;

public class Posti implements Serializable
{
	private ArrayList<Posto> posti;
	
	public Posti()
	{
		this.posti=new ArrayList<Posto>();
	}
	
	public void addPosto(Posto p)
	{
		posti.add(p);
	}
	
	public ArrayList<Posto> getPosti()
	{
		return posti;
	}

}
