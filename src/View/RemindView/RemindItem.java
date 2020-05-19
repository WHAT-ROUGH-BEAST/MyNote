package View.RemindView;

import Model.Note;
import View.ListView.ItemView;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;

public class RemindItem extends ListCell<Note>
{
	@Override
    public void updateItem(Note note, boolean empty)
    {
        super.updateItem(note,empty);
        if(note != null)
        {
            RemindView data = new RemindView();
            data.setInfo(note);
            setGraphic(data.getBox());
        }
    }
}
