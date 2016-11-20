package progettocdr;

import java.io.IOException;

public class PostoNonDisponibileException extends IOException
{
	public PostoNonDisponibileException()
	{
		super("Posto non disponibile!");
	}

}
