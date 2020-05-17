package View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.Editor;
import Controller.UserController;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainView extends View
{
	@FXML Button newButton, importButton,
				exportButton, notebookButton, searchButton;
	
	@FXML NoteView noteviewController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		model = new User();
		model.initialize();
		controller = new UserController(model, this);
		model.addPropertyChangeListener(this);
		
		// 设置用户
		noteviewController.setCurrentNoteBook((User)model);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		
	}
	
	// button事件
	@FXML
	private void newButtonPressAction()
	{
		noteviewController.createNewNote();
	}
	
	@FXML 
	private void importButtonPressAction()
	{
		
	}
	
	@FXML 
	private void notebookButtonPressAction()
	{
		
	}
	
	@FXML 
	private void exportButtonPressAction()
	{
		
	}
	
	@FXML 
	private void searchButtonPressAction()
	{
		
	}
}
