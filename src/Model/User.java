package Model;

import java.beans.PropertyChangeListener;
import java.util.*;

import application.DeepCopy;

public class User extends Model
{
	private String account;
	private String password;
	private List<NoteBook> noteBooks;
	
	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub
		
	}
	
	// getter
	public String getAccount()
	{
		return account;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public List<NoteBook> getNoteBooks()
	{
		return noteBooks;
	}
	
	// setter
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setNoteBooks(List<NoteBook> noteBooks)
	{
		this.noteBooks = (List<NoteBook>)DeepCopy.deepCopy(noteBooks);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		observer.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		observer.removePropertyChangeListener(listener);
	}
}
