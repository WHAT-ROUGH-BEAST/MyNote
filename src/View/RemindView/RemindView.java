package View.RemindView;

import java.io.IOException;
import java.util.ArrayList;

import Model.Note;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RemindView
{
	@FXML HBox hBox;
	@FXML CheckBox checkBox;
	@FXML Label label;
	
	public RemindView()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/RemindView/RemindView.fxml"));
		fxmlLoader.setController(this);
		
		try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
	}
	
	public void setInfo(Note note)
	{
		checkBox.setText(note.getTitle());
		label.setText(note.getAlert().toString());
	}
	
	public HBox getBox()
	{
		return hBox;
	}
}
