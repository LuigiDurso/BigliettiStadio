package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;

public class Biglietti implements Serializable
{
	private ArrayList<Biglietto> biglietti;
	
	public Biglietti()
	{
		biglietti=new ArrayList<Biglietto>();
	}
	
	public double calcolaTotale(String messaggioFlag)
    {
        double totale=0;
        if(messaggioFlag.equals("Tutti"))
        {
            for(Biglietto bigliettiAcquistati:biglietti)
            {
                totale+=bigliettiAcquistati.getPrezzoBiglietto();
            }
        }
        else
        {
            for(Biglietto bigliettiAcquistati:biglietti)
            {
                if(bigliettiAcquistati.getStadio().getNome().equals(messaggioFlag))
                {
                    totale+=bigliettiAcquistati.getPrezzoBiglietto();
                }
            }
        }
            return totale;
    }
	
    public ArrayList<Biglietto> getBiglietti()
    {
        return biglietti;
    }
     
    public void aggiungiBiglietti(Biglietto biglietto)
    {
        biglietti.add(biglietto);
    }

}
