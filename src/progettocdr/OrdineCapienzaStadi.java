package progettocdr;

import java.io.Serializable;

public class OrdineCapienzaStadi implements RelazioneOrdine, Serializable
{
	public int ordina(Object obj1, Object obj2) 
    {
        Partita partita1=(Partita)obj1;
        Partita partita2=(Partita)obj2;
         
        if(partita1.getStadio().getCapienza()>partita2.getStadio().getCapienza())
        {
            return 1;
            
        }
        
        if(partita1.getStadio().getCapienza()<partita2.getStadio().getCapienza())
    	{
    		return -1;
    	}
        
       return 0;
 
    }


}
