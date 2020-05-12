package View;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import View.ListView.ListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class UIController implements Initializable
{
	@FXML private Button btn_1;
	@FXML private Label txt_1;
	@FXML private AnchorPane anchor;
	@FXML private Pane pane;
	@FXML private ListView<String> listview;
	@FXML private ScrollPane scrollpane;
	@FXML private HTMLEditor htmleditor;
	private Button smurfButton;
	
	@FXML
	protected void handleClickAction(ActionEvent Action)
	{
		txt_1.setText("click");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{	
		initList();
		addImgBtn();
		
		listview.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
		htmleditor.setMaxHeight(Double.MAX_VALUE);
		listview.prefHeightProperty().bind(pane.heightProperty());
		
		
	}
	
	private void initList()
	{
		ObservableList<String> observableList = FXCollections.observableArrayList(Arrays.asList("1", "2", "3"));
		listview.setItems(observableList);
		
		// setCellFactory用来设计list中的每一项
		// ListItem继承ListCell<String>，用来装自己的fxml的组件；
		listview.setCellFactory(new Callback<ListView<String>, javafx.scene.control.ListCell<String>>()
        {
            @Override
            public ListCell<String> call(ListView<String> listView)
            {
                return new ListItem();
            }
        });
	}
	
	private void addImgBtn()
	{
		Node node = htmleditor.lookup(".top-toolbar");
		if (node instanceof ToolBar) {
		    ToolBar bar = (ToolBar) node;
		    ImageView graphic = new ImageView(new Image(
		    		"http://bluebuddies.com/gallery/title/jpg/Smurf_Fun_100x100.jpg", 20, 20, true, true));
		    graphic.setEffect(new DropShadow());
		    
		    smurfButton = new Button("", graphic);
		    bar.getItems().add(smurfButton);
		    
		    smurfButton.setOnAction((event) -> onImgBtnAction());
		}
	}
	
	private void onImgBtnAction()
	{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to import");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(htmleditor.getScene().getWindow());
        if (selectedFile != null) {
        //引进文件
//            importDataFile(selectedFile);
        }
	}
	
//	private void importDataFile(File file)
//	{
//		try
//		{
//			if (file.length() > 1024*1024)
//				throw new Exception("File too big");
//			
//			String type = java.nio.file.Files.probeContentType(file.toPath());
//			byte[] data = readFileByBytes(file);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	// 引入图片与引入附件都需要读文件
//	private readFileByBytes(File file) throws
//	{
//		
//	}
}