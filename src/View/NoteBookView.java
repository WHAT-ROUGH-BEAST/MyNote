package View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import Controller.NoteBookController;
import Model.Note;
import Model.NoteBook;
import Model.User;
import View.ListView.ListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

interface NoteListViewInterface
{
	void addNote(Note note);
	void removeNote(int id);
	void setCurrentNoteBook(NoteBook noteBookChoosed);
	void setCurrentNoteListener(View ListListener);
}

// 本区域没有显式的改变model的事件，而用于给外界接口
public class NoteBookView extends View implements NoteListViewInterface
{
	PropertyChangeSupport listObserver = new PropertyChangeSupport(this);
	
	// TODO : 后期改<Note>
	@FXML private ListView<String> noteList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new NoteBook();
		model.initialize();
		controller = new NoteBookController(model, this);
		
		model.addPropertyChangeListener(this);
		
		// 初始化listview
		initNoteList();
	}

	private void initNoteList()
	{
		ArrayList<String> titles = new ArrayList<>();
		for (Note n : ((NoteBook)model).getNotes())
		{
			titles.add(n.getId() + " " + n.getTitle());
		}
		
		ObservableList<String> observableList = FXCollections.observableArrayList(titles);
		noteList.setItems(observableList);
	}
	
	@FXML
	private void onItemClicked()
	{
		// 避免只点了框，没点选项报错np
		if (null == noteList.getSelectionModel().getSelectedItem())
			return;
		
		String noteNameId[] = noteList.getSelectionModel().getSelectedItem().split(" ");

		Note choosedNote = null;
		// TODO: 能不能直接给引用？
		choosedNote = findNoteById(Integer.parseInt(noteNameId[0]));
		
		// 获得点击的note
		listObserver.firePropertyChange("choosedNoteChanged", null, choosedNote);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		//改变list
		switch (evt.getPropertyName())
		{
		case "new notes":
			ArrayList<String> titles = new ArrayList<>();
			for (Note n : (ArrayList<Note>)evt.getNewValue())
			{
				titles.add(n.getId() + " " + n.getTitle());
			}
			noteList.getItems().clear();
			noteList.getItems().addAll(titles);
			break;
		}
	}
	
	private Note findNoteById(int id)
	{
		for (Note n : ((NoteBook)model).getNotes())
		{
			if (n.getId() == id)
			{
				return n;
			}
		}
		throw new RuntimeException("can't find Note with id: "+id);
	}

	@Override
	public void addNote(Note note)
	{
		((NoteBookController)controller).addNote(note);
	}

	@Override
	public void removeNote(int id)
	{
		((NoteBookController)controller).removeNote(id);
	}

	public void setCurrentNoteBook(NoteBook noteBookChoosed)
	{
		if (noteBookChoosed == model)
			return;
		
		// vc中彻底更换list区域的model
		model.removePropertyChangeListener(this);
		
		model = noteBookChoosed;
		((NoteBookController)controller).setCurrentNoteBook(noteBookChoosed);
		model.addPropertyChangeListener(this);
	}

	@Override
	public void setCurrentNoteListener(View ListListener)
	{
		listObserver.addPropertyChangeListener(ListListener);
	}
}
