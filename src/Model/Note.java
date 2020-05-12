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
	private ArrayList<String> labels;
	private Alert alert;
	private NoteContent noteContent;

	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub
		
	}
	
	// getter
	public int getId()
	{
		return id;
	}
	
	public ArrayList<String> getLabels()
	{
		return labels;
	}
	
	public Alert getAlert()
	{
		return alert;
	}
	
	public NoteContent getContent()
	{
		return noteContent;
	}
	
	// setter
	public void setLabels(final ArrayList<String> labels)
	{
		// string本身不可修改，浅复制即可
		this.labels = new ArrayList<String>(labels);
	}
	
	public void setAlert(final Alert alert)
	{
		this.alert = alert;
	}
	
	public void setContent(final NoteContent content)
	{
		this.noteContent = content;
	}
	
	// noteObserver
	// TODO : 指定listner類型
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

class Alert
{
	private boolean state = false;
	private Date date;
	
	public boolean getState()
	{
		return state;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setState(boolean state)
	{
		this.state = state;
	}
	
	public void setDate(final Date date)
	{
		this.date = date;
	}
	
	@Override
	public String toString()
	{
		// 拿去显示的
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		return ft.format(date);
	}
}