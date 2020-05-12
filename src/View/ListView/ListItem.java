package View.ListView;

import javafx.scene.control.ListCell;

public class ListItem extends ListCell<String>
{
    @Override
    public void updateItem(String string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
        	// Data是自定义的数据元素
            ItemView data = new ItemView();
            data.setInfo(string);
            setGraphic(data.getBox());
        }
    }
}
