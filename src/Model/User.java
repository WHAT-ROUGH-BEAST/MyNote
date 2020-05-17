package Model;

import java.beans.PropertyChangeListener;
import java.util.*;

public class User extends Model
{
	private String account;
	private String password;
	private ArrayList<NoteBook> noteBooks;
	
	@Override
	public void initialize()
	{
		// 每个用户初始笔记本：defaultBook
		NoteBook defaultBook = new NoteBook();
		defaultBook.initialize();
		defaultBook.setName("defaultBook");
		noteBooks = new ArrayList<NoteBook>(Arrays.asList(defaultBook));
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
	
	public ArrayList<NoteBook> getNoteBooks()
	{
		return new ArrayList<NoteBook>(noteBooks);
	}
	
	// setter
	public void setAccount(String account)
	{
		observer.firePropertyChange("new account", this.account, account);
		this.account = account;
	}
	
	public void setPassword(String password)
	{
		observer.firePropertyChange("new password", this.password, password);
		this.password = password;
	}
	
	public void setNoteBooks(ArrayList<NoteBook> noteBooks)
	{
		// 提示noteview改变
		ArrayList<String> names = new ArrayList<>();
		for (NoteBook noteBook : noteBooks)
		{
			names.add(noteBook.getName());
		}
		observer.firePropertyChange("noteBookNamesChanged", null, names);
		
		// 
		observer.firePropertyChange("new noteBooks", null, password);
		this.noteBooks = new ArrayList<NoteBook>(noteBooks);
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
