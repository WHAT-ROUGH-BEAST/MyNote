package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.*;

public class Note extends Model
{	
	// 同一个笔记本下的笔记允许同名，所以用id来识别
	private static int cnt = 1;
	private final int id = cnt ++;	
	private String title;
	private ArrayList<String> labels; 
	private Calendar alert;
	private NoteContent noteContent;

	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub
		labels = new ArrayList<String>();
		alert = null;
		noteContent = null;
	}
	
	// getter
	public int getId()
	{
		return id;
	}
	
	public ArrayList<String> getLabels()
	{
		try
		{
			return new ArrayList<String>(this.labels);
		}
		catch (NullPointerException e)
		{
			return null;
		}
	}
	
	public Calendar getAlert()
	{
		return alert;
	}
	
	public NoteContent getContent()
	{
		return noteContent.clone();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	
	// setter
	public void setLabels(final ArrayList<String> labels)
	{
		observer.firePropertyChange("new labels", null, labels);
		if (labels == null)
			this.labels = null;
		else
			this.labels = new ArrayList<String>(labels);
	}
	
	public void setAlert(final Calendar alert)
	{
		observer.firePropertyChange("new alert", null, alert);
		this.alert = alert;
	}
	
	public void setContent(final NoteContent content)
	{
		if (null == content)
			throw new RuntimeException();
		
		observer.firePropertyChange("new content", null, content);
		this.noteContent = content;
	}
	
	public void setTitle(String title)
	{
		observer.firePropertyChange("new title", null, title);
		this.title = title;
	}
	
	// noteObserver
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