package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

import progettocdr.*;

public class Tester implements Serializable
{
	public static void main(String[] argv ) throws FileNotFoundException, ClassNotFoundException, IOException
	{
		LoginComponent frame=new LoginComponent();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
