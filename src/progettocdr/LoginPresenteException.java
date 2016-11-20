package progettocdr;

import java.io.IOException;

public class LoginPresenteException extends IOException
{
	public LoginPresenteException()
	{
		super("Login gia presente!");
	}

}
