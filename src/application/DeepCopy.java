package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DeepCopy
{
	public static <T> T deepCopy(T src) {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out;
		try
		{
			out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  

	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in;
		try
		{
			in = new ObjectInputStream(byteIn);
		    @SuppressWarnings("unchecked")
			T dest = (T) in.readObject();  
		    return dest;
		} 
		catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}  
	}  
}
