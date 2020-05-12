package Model;

import java.beans.PropertyChangeListener;
import java.util.*;

public class NoteBook extends Model
{
	private String name;
	// 接受导出类等，视需求决定
	private List<Note> notes;
	
	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub
		
	}
	
	// getter 
	public String getName()
	{
		return name;
	}

	public List<Note> getNodes()
	{
		return notes;
	}
	
	// setter
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setNotes(List<Note> notes)
	{
		this.notes = notes;
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
