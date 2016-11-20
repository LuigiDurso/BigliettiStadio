package progettocdr;

import java.io.Serializable;

public class Stadio implements Serializable
{
	private String nome;
	private int capienza,capienzaMassima;
	
	public Stadio(String nome,int capienza,int capienzaMassima)
	{
		this.nome=nome;
		this.capienza=capienza;
		this.capienzaMassima=capienzaMassima;
		
	}
	
	public boolean modificaCapienza(int modifica) throws CapacitaException
	{
        if(capienza+modifica>capienzaMassima || capienza+modifica<0)
        {
            throw new CapacitaException();
        }
        else
        {
        	capienza+=modifica;
            return true;
        }
    }


	public String getNome()
	{
		return nome;
	}
	
	public String toString()
	{
		return this.getNome();
	}
	
	public int getCapienza()
	{
		return capienza;
	}


}
