package View;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.*;

import Controller.UserController;
import Model.Note;
import Model.NoteBook;
import Model.User;
import View.ListView.RemindItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

interface AllBookViewInterface
{
	void addNoteToBook(Note note, String noteBookChoosed);
	void removeNoteFromBook(int id, String noteBookChoosed);
	void setCurrentNoteListener(View Listlistener);
	ArrayList<String> getUserNoteBookNames();
	void setNoteBookNameListener(View listener);
	void setCurrentNoteBook(User currentUser);
}

public class AllBookView extends View implements AllBookViewInterface
{
	@FXML private ListView<RemindItem> remindList;
	@FXML private TextField searchText;
	@FXML private Button searchButton;
	@FXML private ChoiceBox<String> noteBookChooser;
	private String noteBookChoosed;

	@FXML private NoteBookView notelistviewController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO : new user有问题，应该和登录的user保持一致，
		// 但是问题不大，到时候完全复制就可
		model = new User();
		model.initialize();
		controller = new UserController(model, this);
		model.addPropertyChangeListener(this);  
		
		// TODO : 初始化组件
		// initRemindList();
		initNoteBookChooser();
	}
	
	private void initNoteBookChooser()
	{
		noteBookChoosed = "defaultBook";
		noteBookChooser.setValue(noteBookChoosed);
		
		ArrayList<String> noteBookNames = getUserNoteBookNames();
		noteBookChooser.setItems(FXCollections.observableArrayList(noteBookNames));
		noteBookChooser.scaleXProperty().addListener(new ChangeListener<Number>() { 
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
			{
				noteBookChoosed = noteBookNames.get(arg1.intValue());
				for (NoteBook noteBook : ((User)model).getNoteBooks())
				{
					if (noteBook.getName().equals(noteBookChoosed))
						notelistviewController.setCurrentNoteBook(noteBook);
				}
			} 
        }); 
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		// TODO Auto-generated method stub
		switch (evt.getPropertyName())
		{
		case "new noteBooks":
			noteBookChooser.getItems().clear();
			noteBookChooser.getItems().addAll((ArrayList<String>)evt.getNewValue());
			break;
		default:
			break;
		}
	}

	// list的notebook与user中的notebook已同步
	@Override
	public void addNoteToBook(Note note, String noteBookChoosed)
	{
		final User thisUser= (User)model;
		// 找到本笔记本
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookChoosed))
			{
				notelistviewController.setCurrentNoteBook(nb);
				notelistviewController.addNote(note);
				return;
			}
		}
		
		throw new RuntimeException("can't find notebook named: \"" + noteBookChoosed + "\"");
	}

	@Override
	public void removeNoteFromBook(int id, String noteBookChoosed)
	{
		final User thisUser= (User)model;
		// 找到本笔记本
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookChoosed))
			{
				notelistviewController.setCurrentNoteBook(nb);
				notelistviewController.removeNote(id);
				return;
			}
		}
		
		throw new RuntimeException("can't find notebook named: \"" + noteBookChoosed + "\"");
	}
	

	@Override
	public void setCurrentNoteListener(View Listlistener)
	{
		notelistviewController.setCurrentNoteListener(Listlistener);
	}

	@Override
	public ArrayList<String> getUserNoteBookNames()
	{
		ArrayList<NoteBook> noteBooks = ((User)model).getNoteBooks();
		ArrayList<String> names = new ArrayList<String>();
		for (NoteBook book : noteBooks)
		{
			names.add(book.getName());
		}
		
		return names;
	}

	@Override
	public void setNoteBookNameListener(View listener)
	{
		model.addPropertyChangeListener(listener);
	}

	@Override
	public void setCurrentNoteBook(User currentUser)
	{
		if (model == currentUser)
			return;
		
		model.removePropertyChangeListener(this);
		
		model = currentUser;
		((UserController)controller).setCurrentNoteBook(currentUser);
		model.addPropertyChangeListener(this);
	}
}
