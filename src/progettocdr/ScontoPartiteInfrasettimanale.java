package progettocdr;

import java.io.Serializable;

public class ScontoPartiteInfrasettimanale implements Serializable
{
	private static final int scontoInfrasettimanle= 5;
    
    public float sconta(Object prezzo,Object paragone) 
    {
    	float prezzoBiglietto=(float) prezzo;
    	int paragoneSconto=(int) paragone;
         
        if(paragoneSconto<5)
        {
            return prezzoBiglietto-((prezzoBiglietto/100)*scontoInfrasettimanle);
        }
        else
        {
            return prezzoBiglietto;
        }
    }

}
