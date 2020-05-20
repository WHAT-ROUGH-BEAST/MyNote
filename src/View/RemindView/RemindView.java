package View.RemindView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	private Remind dataHolder;
	
	private void setData(Remind data)
	{
		dataHolder = data;
	}
	
	public RemindView()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/RemindView/RemindItem.fxml"));
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
	
	public void setInfo(Remind data)
	{
		setData(data);
		checkBox.setText(data.getNote().getTitle() + ":" + data.getBookName());
		label.setText(data.getNote().getAlert().get(Calendar.YEAR)  + "/" 
					+(data.getNote().getAlert().get(Calendar.MONTH) + 1) + "/"
					+ data.getNote().getAlert().get(Calendar.DAY_OF_MONTH));
	}
	
	public HBox getBox()
	{
		return hBox;
	}
}
