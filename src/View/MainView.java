package View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

import Controller.Editor;
import Controller.IOOperator;
import Controller.UserController;
import Model.Model;
import Model.Note;
import Model.NoteBook;
import Model.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

interface MainViewInterface
{
	void setCloseWindowEvent(Stage stage);
}

public class MainView extends View implements MainViewInterface
{
	@FXML Button newButton, importButton,
				exportButton, notebookButton, searchButton;
	
	@FXML NoteView noteviewController;
	@FXML VBox root;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new User();
		model.initialize();
		controller = new UserController(model, this);
		model.addPropertyChangeListener(this);
		
		// 设置用户
		noteviewController.setCurrentUser((User)model);
	}
	
	private User readUserInfo(String Account)
	{
		try
		{
			return (User)IOOperator.deserialize(".\\outputFile\\" 
					+ Account +".user");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{

	}
	
	// button事件
	@FXML
	private void newButtonPressAction()
	{		
		Note newNote = new Note();
		newNote.initialize();
		noteviewController.updateNote(newNote);
	}
	
	@FXML 
	private void importButtonPressAction()
	{
		try
		{
			File choosedFile = IOOperator.ChooseFile(importButton.getScene().getWindow(), "Note");
			String path = choosedFile.getAbsolutePath();
			Note importNote = (Note)IOOperator.deserialize(path);
	        noteviewController.updateNote(importNote);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML 
	private void notebookButtonPressAction()
	{
		ArrayList<String> names = new ArrayList<>();
		for (NoteBook book : ((User)model).getNoteBooks())
		{
			names.add(book.getName());
		}
		
		TextInputDialog dialog = new TextInputDialog("newNotebook");
		dialog.setTitle("Add A NoteBook");
		dialog.setHeaderText("current NoteBooks : \n" + names);
		dialog.setContentText("Please enter your alert time:");
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(bookName -> addNoteBook(bookName));
	}
	
	private void addNoteBook(String bookName)
	{
		((UserController)controller).addNoteBook(bookName);
	}
	
	@FXML 
	private void exportButtonPressAction()
	{
		Note note = noteviewController.getCurrentNote();
		String name = (note.getTitle() == null)?"new":note.getTitle();
		String path = IOOperator.ChooseFolder(exportButton.getScene().getWindow());
		try
	    {
			IOOperator.serialize(path + "\\" + name + ".note", note);
	    }
		catch(IOException e)
	    {
	        System.out.println("didn't choose a file");
	    }
	}
	
	@FXML 
	private void searchButtonPressAction()
	{
		
	}

	// 关闭时序列化
	@Override
	public void setCloseWindowEvent(Stage primaryStage)
	{
		primaryStage.setOnCloseRequest((WindowEvent event) ->
		{
			try
			{
				IOOperator.serialize(".\\outputFile\\" 
							+ ((User)model).getAccount()+".user", model);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			System.exit(0);
		});
	}

}
