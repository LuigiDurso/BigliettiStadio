package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Partita implements Serializable
{
	private String squadraA,squadraB;
	private GregorianCalendar dataPartita;
	private Stadio stadio;
	private float costoBiglietto;
	private ArrayList<Posto> posti;
	private boolean[] sconti;
	
	public Partita(String squadraA,String squadraB,GregorianCalendar dataPartita,Stadio stadio,float costoBiglietto)
	{
		this.squadraA=squadraA;
		this.squadraB=squadraB;
		this.dataPartita=dataPartita;
		this.stadio=stadio;
		this.costoBiglietto=costoBiglietto;
		this.posti=new ArrayList<>();
		this.inizializzaPosti();
		sconti=new boolean[3];
		this.inizializzaSconti();
	}
	
	private void inizializzaSconti()
	{
		for(int i=0;i<3;i++)
		{
			sconti[i]=false;
		}
	}
	
	public boolean[] getSconti()
	{
		return sconti;
	}
	
	public boolean getSconto(int i)
	{
		return sconti[i];
	}
	

	private void inizializzaPosti()
	{
		for(int i=1;i<=stadio.getCapienza();i++)
		{
			Posto p=new Posto(i,1);
			posti.add(p);
		}
	}
	
	public void setPosto(Posto p,int status)
	{
		int numeroPosto=p.getNumPosto();
		
		for(Posto posto:posti)
		{
			if(numeroPosto==posto.getNumPosto())
			{
				posto.setStatus(status);
			}
		}
	}
	
	public Posto getPosto(int i)
	{
		for(Posto posto:posti)
		{
			if(i==posto.getNumPosto())
			{
				return posto;
			}
		}
		return null;
		
	}
	
	public ArrayList<Posto> getPosti()
	{
		return posti;
	}
	
	
	public void attivaDisattivaScontoPartita(int i,boolean b)
	{
        sconti[i]=b;
    }
	
	public float getCostoBiglietto()
	{
		return costoBiglietto;
	}
	
	public String getSquadraCasa()
	{
		return squadraA;
	}
	
	public String getSquadraTrasferta()
	{
		return squadraB;
	}
	
	public GregorianCalendar getData()
	{
		return dataPartita;
	}
	
	public Stadio getStadio()
	{
		return stadio;
	}
	
	public void setCostoBiglietto(float quantita)
	{
        this.costoBiglietto=quantita;
    }
	
	public boolean controlloOrarioPartita() throws PartitaNonPrenotabileException
	{
		GregorianCalendar dataAttuale=new GregorianCalendar();
		
		dataAttuale.add(GregorianCalendar.HOUR_OF_DAY, 12);
		
		
		if(dataAttuale.before(dataPartita))
		{
			return true;
		}
		else
		{
			throw new PartitaNonPrenotabileException();
		}
	}
	
	public boolean equals(Object obj)
	{
		Partita p1=(Partita) obj;
		
		int giorno=dataPartita.get(GregorianCalendar.DAY_OF_MONTH);
		int mese=dataPartita.get(GregorianCalendar.MONTH);
		int anno=dataPartita.get(GregorianCalendar.YEAR);
		
		int giornoObj=p1.getData().get(GregorianCalendar.DAY_OF_MONTH);
		int meseObj=p1.getData().get(GregorianCalendar.MONTH);
		int annoObj=p1.getData().get(GregorianCalendar.YEAR);
		
		return squadraA.equals(p1.getSquadraCasa()) && squadraB.equals(p1.getSquadraTrasferta())
				&& giorno==giornoObj && mese==meseObj && anno==annoObj;
	}
	public String toString()
	{
		int giorno=dataPartita.get(GregorianCalendar.DAY_OF_MONTH);
		int mese=dataPartita.get(GregorianCalendar.MONTH)+1;
		int anno=dataPartita.get(GregorianCalendar.YEAR);
		int minuto=dataPartita.get(GregorianCalendar.MINUTE);
		int ora=dataPartita.get(GregorianCalendar.HOUR_OF_DAY);
		
		return ""+squadraA+"-"+squadraB+" in data: "+giorno+"/"+mese+"/"+anno+"-"+ora+":"+minuto+" Presso : "+this.getStadio().getNome();
	}
}
