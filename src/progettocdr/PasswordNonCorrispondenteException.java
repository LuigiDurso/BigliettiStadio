package progettocdr;

import java.io.IOException;

public class PasswordNonCorrispondenteException extends IOException 
{
	public PasswordNonCorrispondenteException()
	{
		super("Verifica Password non corrispondete!");
	}

}
