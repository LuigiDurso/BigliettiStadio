package progettocdr;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class ScontoEtaSpettatore implements Sconto , Serializable
{
	private static final int percentualeBambini= 35;
    private static final int percentualeAnziani= 25;
     
    public float sconta(Object prezzo,Object paragone) 
    {
    	float prezzoBiglietto=(float) prezzo;
    	int paragoneSconto=(int) paragone;
     
        GregorianCalendar dataAttuale=new GregorianCalendar();
        int annoAttuale= dataAttuale.get(GregorianCalendar.YEAR);
         
        if(annoAttuale-paragoneSconto<12)
        {
        	return prezzoBiglietto-((prezzoBiglietto/100)*percentualeBambini);
        }
        else 
        	if(annoAttuale-paragoneSconto>60)
        	{
                return prezzoBiglietto-((prezzoBiglietto/100)*percentualeAnziani);
            }else
            {
                return prezzoBiglietto;
            }
        }

}
