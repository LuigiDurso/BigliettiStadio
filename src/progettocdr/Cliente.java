package progettocdr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Cliente extends Persona implements Serializable
{
	private ArrayList<Biglietto> bigliettiPrenotati,bigliettiAcquistati;
	
	public Cliente(String nome,String cognome,GregorianCalendar dataNascita,String login,String password)
	{
		super(nome,cognome,dataNascita,login,password);
		this.bigliettiAcquistati=new ArrayList<>();
		this.bigliettiPrenotati=new ArrayList<>();
	}
	
	public boolean verificaPrenotazioneAcquistoPartita(Partita p) throws PartitaGiaPrenotataAcquistataException
	{
		
		for(Biglietto b1:bigliettiPrenotati)
		{
			
			if(b1.getPartita().equals(p))
			{
				throw new PartitaGiaPrenotataAcquistataException();
			}
		}
		for(Biglietto b2:bigliettiAcquistati)
		{
			
			if(b2.getPartita().equals(p))
			{
				throw new PartitaGiaPrenotataAcquistataException();
			}
		}
		return true;
	}
	
	public void addPrenotazione(Biglietto b)
	{
		bigliettiPrenotati.add(b);
	}
	
	public void addAcquisto(Biglietto b)
	{
		bigliettiAcquistati.add(b);
	}
	
	public ArrayList<Biglietto> getPrenotazioni()
	{
		return bigliettiPrenotati;
	}
	
	public ArrayList<Biglietto> getAcquisti()
	{
		return bigliettiAcquistati;
	}
	
	public void deletePrenotazione(Biglietto b)
	{
		bigliettiPrenotati.remove(b);
	}

	public boolean searchBiglietto(Biglietto b)
	{
		return bigliettiAcquistati.contains(b) || bigliettiPrenotati.contains(b);
	}
	
	public ArrayList<Biglietto> getBigliettiPrenotati()
	{
		return bigliettiPrenotati;
	}
	
	public ArrayList<Biglietto> getBigliettiAcquistati()
	{
		return bigliettiAcquistati;
	}
}
