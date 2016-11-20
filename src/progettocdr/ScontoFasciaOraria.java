package progettocdr;

import java.io.Serializable;

public class ScontoFasciaOraria implements Sconto, Serializable
{
	private static final int scontoMattina=10;
	
    public float sconta(Object prezzo,Object paragone) 
    {
    	float prezzoBiglietto=(float) prezzo;
    	int paragoneSconto=(int) paragone;
    	
        if(paragoneSconto<13)
        {
            return prezzoBiglietto-((prezzoBiglietto/100)*scontoMattina);
        }
        else
        {
            return prezzoBiglietto;
        }
    }
	

}
