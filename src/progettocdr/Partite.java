package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;

public class Partite implements Serializable
{
	private ArrayList<Partita> partite;
	
	public Partite()
	{
		this.partite=new ArrayList<>();
	}
	
	public ArrayList<Partita> getPartite()
	{
		return partite;
	}
	
	public void addPartita(Partita p)
	{
		partite.add(p);
	}
	
	public void ordinaPartite(RelazioneOrdine ordine)
	{
		int  minimo;
		Partita temp;
		
		for(int i=0;i<partite.size();i++)
		{
			minimo=i;
			for(int j=i;j<partite.size();j++)
			{
				if((ordine.ordina(partite.get(minimo), partite.get(j)))>0)
					minimo=j;
			}
			temp=partite.get(minimo);
			partite.set(minimo, partite.get(i));
			partite.set(i, temp);
		}
	}
	

}
