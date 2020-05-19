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
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

interface NoteListViewInterface
{
	NoteBook addNote(Note note);
	NoteBook removeNote(int id);
	void setCurrentNoteBook(NoteBook noteBookChoosed);
	void setCurrentNoteListener(View ListListener);
}

// ������û����ʽ�ĸı�model���¼��������ڸ����ӿ�
public class NoteBookView extends View implements NoteListViewInterface
{
	PropertyChangeSupport listObserver = new PropertyChangeSupport(this);
	
	@FXML private ListView<String> noteList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new NoteBook();
		model.initialize();
		controller = new NoteBookController(model, this);
		
		model.addPropertyChangeListener(this);
		
		// ��ʼ��listview
		initNoteList();
	}

	private void initNoteList()
	{
		ArrayList<String> names = new ArrayList<String>();
		for (Note n : ((NoteBook)model).getNotes())
		{
			names.add(n.getId() + " " + n.getTitle());
		}
		
		noteList.setItems(FXCollections.observableArrayList(names));
	}
	
	@FXML
	private void onItemClicked()
	{
		// ����ֻ���˿�û��ѡ���np
		if (null == noteList.getSelectionModel().getSelectedItem())
			return;
		
		String noteNameId[] = noteList.getSelectionModel().getSelectedItem().split(" ");

		Note choosedNote = findNoteById(Integer.parseInt(noteNameId[0]));
		
		// ��õ����note
		listObserver.firePropertyChange("listChoosedNoteChanged", null, choosedNote);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		//�ı�list
		switch (evt.getPropertyName())
		{
		case "new notes":
			ArrayList<String> names = new ArrayList<String>();
			for (Note n : ((ArrayList<Note>)evt.getNewValue()))
			{
				names.add(n.getId() + " " + n.getTitle());
			}
			noteList.getItems().clear();
			noteList.getItems().addAll(names);
			break;
		}
	}
	
	private Note findNoteById(int id)
	{
		for (Note n : ((NoteBook)model).getNotes())
		{
			if (n.getId() == id)
			{
				return n.clone();
			}
		}
		throw new RuntimeException("can't find Note with id: "+id);
	}

	@Override
	public NoteBook addNote(Note note)
	{
		return ((NoteBookController)controller).addNote(note);
	}

	@Override
	public NoteBook removeNote(int id)
	{
		return ((NoteBookController)controller).removeNote(id);
	}

	public void setCurrentNoteBook(NoteBook noteBook)
	{	
		model.removePropertyChangeListener(this);
		model = noteBook.clone();
		model.addPropertyChangeListener(this);
		((NoteBookController)controller).setCurrentNoteBook(model);
	}

	@Override
	public void setCurrentNoteListener(View ListListener)
	{
		listObserver.addPropertyChangeListener(ListListener);
	}
}
