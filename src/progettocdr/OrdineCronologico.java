package progettocdr;

import java.io.Serializable;

public class OrdineCronologico implements RelazioneOrdine, Serializable
{

	public int ordina(Object obj1, Object obj2) 
	{
		Partita partita1=(Partita) obj1;
		Partita partita2=(Partita) obj2;
		
		if(partita1.getData().before(partita2.getData()))
		{
			return -1;
		}
		else
		{
			if(partita1.getData().equals(partita2.getData()))
			{
				return 0;
			}
			else
				return 1;
		}
			
		
	}

}
