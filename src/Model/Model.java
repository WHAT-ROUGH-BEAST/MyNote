package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Model implements Cloneable
{
	protected PropertyChangeSupport observer = new PropertyChangeSupport(this);
	
	public abstract void initialize();
	
	public abstract void addPropertyChangeListener(PropertyChangeListener listener);
	public abstract void removePropertyChangeListener(PropertyChangeListener listener);
	
	@Override
	public abstract Model clone();
}