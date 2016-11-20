package progettocdr;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Biglietto  implements Serializable
{
	private Cliente cliente;
	private Partita partita;
	private float prezzo;
	private Posto p;
	
	public Biglietto(Cliente cliente,Partita partita,float prezzo,Posto p)
	{
		this.cliente=cliente;
		this.partita=partita;
		this.prezzo=prezzo;
		this.p=p;
	}
	
	public int getNumeroPosto()
	{
		return p.getNumPosto();
	}
	
	
	public Partita getPartita()
	{
		return partita;
	}
	
	public boolean controlloPartitaBiglietto() throws PartitaNonPrenotabileException
	{
		GregorianCalendar dataPartita=partita.getData();
		GregorianCalendar dataAttuale=new GregorianCalendar();
		
		dataAttuale.add(GregorianCalendar.HOUR_OF_DAY, 12);
		
		if(dataAttuale.before(dataPartita))
		{
			return true;
		}
		else
		{
			this.cancellaPrenotazione();
			throw new PartitaNonPrenotabileException();
		}
	}
	
	public void setPrezzoBiglietto()
    {
        boolean[] sconti=partita.getSconti();
        
        float[] prezziScontati={prezzo,prezzo,prezzo};
        
        if(sconti[0])
        {
        	ScontoEtaSpettatore sconto1=new ScontoEtaSpettatore();
        	prezziScontati[0]=sconto1.sconta(prezzo,cliente.getNascitaUtente().get(GregorianCalendar.YEAR));
        }
        
       if(sconti[1])
       {
    	   ScontoFasciaOraria sconto2=new ScontoFasciaOraria();
    	   prezziScontati[1]=sconto2.sconta(prezzo, partita.getData().get(GregorianCalendar.HOUR_OF_DAY));
       }
        
       if(sconti[2])
       {
    	   ScontoPartiteInfrasettimanale sconto3=new ScontoPartiteInfrasettimanale();
    	   prezziScontati[2]=sconto3.sconta(prezzo, partita.getData().get(GregorianCalendar.DAY_OF_WEEK));
       }
  
        
        float prezzoMinimo=prezziScontati[0];
        for(int i=0;i<3;i++)
        {
        	if(prezziScontati[i]<=prezzoMinimo)
        	{
        		prezzoMinimo=prezziScontati[i];
        	}
        }
        
        prezzo=prezzoMinimo;
                
    }
	
	public Cliente getCliente()
	{
		return cliente;
	}
	
	public void prenotaBiglietto()
	{
		cliente.addPrenotazione(this);
		partita.setPosto(p, 0);
		
	}
	
	public void acquistaBiglietto()
	{
		cliente.addAcquisto(this);
		cliente.deletePrenotazione(this);
		partita.setPosto(p, -1);
	}
	
	public void cancellaPrenotazione()
	{
		cliente.deletePrenotazione(this);
		partita.setPosto(p, 1);
	}
	
	
	public float getPrezzoBiglietto()
	{
		return prezzo;
	}
	
	public Stadio getStadio()
	{
		return partita.getStadio();
	}
	
	public String toString()
	{
		return cliente.getNome()+" "+cliente.getCognome()+" "+partita.toString()+" prezzo: "+prezzo+" posto:"+p.getNumPosto();
	}

}
