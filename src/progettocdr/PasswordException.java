package progettocdr;

import java.io.IOException;

public class PasswordException extends IOException 
{
	public PasswordException()
	{
		super("La password deve contenere numeri e lettere!");
	}

}
