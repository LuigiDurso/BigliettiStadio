package progettocdr;

import java.io.IOException;

public class PartitaGiaPrenotataAcquistataException extends IOException 
{
	public PartitaGiaPrenotataAcquistataException()
	{
		super("Partita gia prenotata/acquistata da questo cliente!!!");
	}

}
