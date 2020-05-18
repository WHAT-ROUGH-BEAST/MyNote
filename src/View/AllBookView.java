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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
	@FXML private ChoiceBox<String> listChooser;
	@FXML private Button notifyListButton;
	@FXML private NoteBookView notelistviewController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new User();
		model.initialize();
		controller = new UserController(model, this);
		model.addPropertyChangeListener(this);  
		
		// TODO : ��ʼ�����
		// initRemindList();
		initListChooser();
	}
	
	private void initListChooser()
	{
		listChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() { 
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2)
			{
				notifyListButton.setText((arg2 == null)?arg0.getValue():arg2);
			} 
        }); 
	}
	
	@FXML
	private void notifyListButtonPressAction()
	{
		for (NoteBook book : ((User)model).getNoteBooks())
		{
			if (book.getName().equals(notifyListButton.getText()))
			{
				notelistviewController.setCurrentNoteBook(book);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		// TODO Auto-generated method stub
		switch (evt.getPropertyName())
		{
		case "new noteBooks":
			String chooseHelper = notifyListButton.getText();
			listChooser.getItems().clear();
			listChooser.getItems().addAll((ArrayList<String>)evt.getNewValue());
			// ����choicebox�ı��ʧbutton text
			notifyListButton.setText(chooseHelper);
			break;
		default:
			break;
		}
	}

	@Override
	public void addNoteToBook(Note note, String noteBookName)
	{
		final User thisUser= (User)model;
		// �ҵ����ʼǱ�
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookName))
			{
				notelistviewController.setCurrentNoteBook(nb);
				NoteBook updatedNoteBook = notelistviewController.addNote(note);
				
				// �ı�chooser��ͼ
				notifyListButton.setText(noteBookName);
				
				// user�������
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
		// �ҵ����ʼǱ�
		for (NoteBook nb : thisUser.getNoteBooks())
		{
			if (nb.getName().equals(noteBookName))
			{
//				notelistviewController.setCurrentNoteBook(nb);
				NoteBook updatedNoteBook = notelistviewController.removeNote(id);
				
				// �ı�chooser��ͼ
				notifyListButton.setText(noteBookName);
				
				// user�������
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
	}
}
