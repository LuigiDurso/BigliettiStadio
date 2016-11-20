package progettocdr;

import java.io.IOException;

public class PartitaNonPrenotabileException extends IOException 
{
	public PartitaNonPrenotabileException()
	{
		super("Partita non prenotabile: Tempo Scaduto");
	}

}
