package progettocdr;

import java.io.Serializable;

public class OrdineStadio implements RelazioneOrdine, Serializable
{
	public int ordina(Object obj1, Object obj2) 
	{
		Partita partita1=(Partita) obj1;
		Partita partita2=(Partita) obj2;
		
		return partita1.getStadio().getNome().compareTo(partita2.getStadio().getNome());
	}

}
