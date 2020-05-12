package View.ListView;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ItemView
{
	@FXML Label label_1;
	@FXML Label label_2;
	@FXML VBox vbox;
	
	public ItemView()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resource/ListItem.fxml"));
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
	
	public void setInfo(String string)
	{
		label_1.setText(string);
		label_2.setText(string);
	}
	
	public VBox getBox()
	{
		return vbox;
	}
}
