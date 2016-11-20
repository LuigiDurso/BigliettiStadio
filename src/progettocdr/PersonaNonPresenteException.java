package progettocdr;

import java.io.IOException;

public class PersonaNonPresenteException extends IOException 
{
	public PersonaNonPresenteException()
	{
		super("Nessuna persona trovata nel database!");
	}

}
