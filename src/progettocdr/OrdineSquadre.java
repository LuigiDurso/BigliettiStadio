package progettocdr;

import java.io.Serializable;

public class OrdineSquadre implements RelazioneOrdine, Serializable
{
	public int ordina(Object obj1, Object obj2) 
	{
		Partita partita1=(Partita) obj1;
		Partita partita2=(Partita) obj2;
		
		if(partita1.getSquadraCasa().compareTo(partita2.getSquadraCasa())<0)
		{
			if(partita1.getSquadraTrasferta().compareTo(partita2.getSquadraTrasferta())<0)
			{
				return -1;
			}
			else
				return 1;
		}
		else
			return 1;
		
	}

}
