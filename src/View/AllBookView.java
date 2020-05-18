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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

interface AllBookViewInterface
{
	void addNoteToBook(Note note, String noteBookChoosed);
	void removeNoteFromBook(int id, String noteBookChoosed);
	void setCurrentNoteListener(View Listlistener);
	ArrayList<String> getUserNoteBookNames();
	void setNoteBookNameListener(View listener);
	void setCurrentUser(User currentUser);
}

public class AllBookView extends View implements AllBookViewInterface
{
	@FXML private ListView<RemindItem> remindList;
	@FXML private TextField searchText;
	@FXML private Button searchButton;
	@FXML private ChoiceBox<String> ListChooser;
	// 记录当前选中的笔记本以便控制chooser视图
	private String choosedNoteBook;

	@FXML private NoteBookView notelistviewController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new User();
		model.initialize();
		controller = new UserController(model, this);
		model.addPropertyChangeListener(this);  
		
		// TODO : 初始化组件
		// initRemindList();
		initListChooser();
	}
	
	private void setChoosedNoteBook(String noteBookName)
	{
		choosedNoteBook = noteBookName;
	}
	
	private void initListChooser()
	{
		ArrayList<String> noteBookNames = getUserNoteBookNames();
		ListChooser.setItems(FXCollections.observableArrayList(noteBookNames));
		ListChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() { 
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2)
			{
				setChoosedNoteBook(arg2);
				
				for (NoteBook book : ((User)model).getNoteBooks())
				{
					if (book.getName().equals(choosedNoteBook))
						notelistviewController.setCurrentNoteBook(book.clone());
					return;
				}
			} 
        }); 
		
		setChoosedNoteBook("defaultBook");
		ListChooser.setValue(choosedNoteBook);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		// TODO Auto-generated method stub
		switch (evt.getPropertyName())
		{
		case "new noteBooks":
			ListChooser.getItems().clear();
			try
			{
				ListChooser.getItems().addAll((ArrayList<String>)evt.getNewValue());
				ListChooser.setValue(choosedNoteBook);
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void addNoteToBook(Note note, String noteBookName)
	{
		final User thisUser= (User)model;
		// 找到本笔记本
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookName))
			{
				notelistviewController.setCurrentNoteBook(nb);
				NoteBook updatedNoteBook = notelistviewController.addNote(note);
				
				// 改变chooser视图
				setChoosedNoteBook(noteBookName);
				
				// user本身添加
				((UserController)controller).updateNoteBook(updatedNoteBook);
				return;
			}
		}
		
		throw new RuntimeException("can't find notebook named: \"" + noteBookName + "\"");
	}

	@Override
	public void removeNoteFromBook(int id, String noteBookName)
	{
		final User thisUser= (User)model;
		// 找到本笔记本
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookName))
			{
				notelistviewController.setCurrentNoteBook(nb);
				NoteBook updatedNoteBook = notelistviewController.removeNote(id);
				
				// 改变chooser视图
				setChoosedNoteBook(noteBookName);
				
				// user本身添加
				((UserController)controller).updateNoteBook(updatedNoteBook);
				return;
			}
		}
		
		throw new RuntimeException("can't find notebook named: \"" + noteBookName + "\"");
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
	public void setCurrentUser(final User currentUser)
	{
		if (model == currentUser)
			return;
		
		model.removePropertyChangeListener(this);
		model = currentUser;
		model.addPropertyChangeListener(this);
		((UserController)controller).setCurrentUser(model);
		// TODO : 改变视图 -- choiceBox
	}
}
