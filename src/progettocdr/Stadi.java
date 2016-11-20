package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;

public class Stadi implements Serializable
{
	private ArrayList<Stadio> stadi;
	
	public Stadi()
	{
		this.stadi=new ArrayList<>();
	}
	
	public ArrayList<Stadio> getStadi()
	{
		return stadi;
	}
	
	public void addStadio(Stadio s)
	{
		stadi.add(s);
	}

}
