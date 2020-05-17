package View;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import Controller.NoteController;
import Model.Note;
import Model.NoteBook;
import Model.NoteContent;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

interface NoteViewInterface
{
	void setCurrentNoteBook(User currentUser);
	void createNewNote();
}

public class NoteView extends View implements CurrentNoteListener, NoteViewInterface
{
	@FXML private TextField titleText, remindTimeText;
	@FXML private Label labelText;
	@FXML private Button deleteButton, remindButton, 
				doneButton, labelButton;
	@FXML private ChoiceBox<String> noteBookChooser;
	private String noteBookChoosed;
	
	// 用于控制editor以及notebook区域
	@FXML private EditorView editorviewController;
	@FXML private AllBookView allbookviewController;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// mvc
		model = new Note();
		model.initialize();
		controller = new NoteController(model, this);
		
		model.addPropertyChangeListener(this);
		//注册list的listener 读取来自notebook的信息
		setCurrentNoteListener(this);
		
		// 组件初始化
		remindTimeText.setDisable(true);
		initNoteBookChooser();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		switch (evt.getPropertyName())
		{
		case "new labels":
			String labels = getLabelsText((ArrayList<String>)evt.getNewValue());
			labelText.setText(labels);
			break;
		case "new alert":
			remindTimeText.setText(getCalendarStr((Calendar)evt.getNewValue()));
			break;
		case "new content":
			editorviewController.setCurrentContent((NoteContent)evt.getNewValue());
			break;
		case "new title":
			titleText.setText(((String)evt.getNewValue()).toString());
			break;
		case "choosedNoteChanged":
			setCurrentNote((Note)evt.getNewValue());
			break;
		case "noteBookNamesChanged":
			noteBookChooser.getItems().clear();
			noteBookChooser.getItems().addAll((ArrayList<String>)evt.getNewValue());
			break;
		default:
			break;
		}
	}
	
	private String getLabelsText(ArrayList<String> strs)
	{
		if (null == strs || strs.isEmpty())
			return "";
		
		StringBuilder builder = new StringBuilder();
		for (String s : strs)
		{
			builder.append(s);
			builder.append(",");
		}
		
		return builder.toString();
	}
	
	private void setCurrentNote(Note choosedNote)
	{
		if (null == choosedNote)
			return;
		
		String labels = null;
		if (null != choosedNote.getLabels())
			labels = getLabelsText(choosedNote.getLabels());
	
		((NoteController)controller).updateNote(
				choosedNote.getTitle(),
				labels,
				getCalendarStr(choosedNote.getAlert()),
				choosedNote.getContent());
		
		// 更新视图
		editorviewController.setCurrentContent(choosedNote.getContent());
	}
	
	private String getCalendarStr(Calendar cal)
	{
		if (null == cal)
			return "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		String formatted = format1.format(cal.getTime());
		return formatted.toString();
	}
	
	private void setCurrentNoteListener(View Listlistner)
	{
		allbookviewController.setCurrentNoteListener(Listlistner);
	}
	
	// 点击事件
	@FXML
	private void doneButtonPressAction()
	{
		// 保存笔记内容
		((NoteController)controller).updateNote(
				titleText.getText(), 
				labelText.getText(), 
				remindTimeText.getText(),
				editorviewController.updateContent());
		
		// 通知对应的notebook添加本note
		allbookviewController.addNoteToBook((Note)model, noteBookChoosed);
	}
	
	@FXML
	private void deleteButtonPressAction()
	{
		// 通知对应的notebook删除本note
		allbookviewController.removeNoteFromBook(((Note)model).getId(), noteBookChoosed);
	}
	
	@FXML
	private void remindButtonPressAction()
	{
		TextInputDialog dialog = new TextInputDialog("2020/05/16");
		dialog.setTitle("alert");
		dialog.setHeaderText("set your alert");
		dialog.setContentText("Please enter your alert time:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(alert -> remindTimeText.setText(alert));
	}

	@FXML
	private void labelButtonPressAction()
	{
		TextInputDialog dialog = new TextInputDialog("label1,label2");
		dialog.setTitle("labels");
		dialog.setHeaderText("set your labels");
		dialog.setContentText("Please enter your labels:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(labels -> labelText.setText(labels));
	}
	
	private void initNoteBookChooser()
	{
		noteBookChoosed = "defaultBook";
		noteBookChooser.setValue(noteBookChoosed);
		
		// 读入笔记本名，并注册笔记本名的监听者
		ArrayList<String> noteBookNames = allbookviewController.getUserNoteBookNames();
		allbookviewController.setNoteBookNameListener(this);
		
		noteBookChooser.setItems(FXCollections.observableArrayList(noteBookNames));
		noteBookChooser.scaleXProperty().addListener(new ChangeListener<Number>() { 
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
			{
				noteBookChoosed = noteBookNames.get(arg1.intValue());
			} 
        }); 
	}

	@Override
	public void setCurrentNoteBook(User currentUser)
	{
		allbookviewController.setCurrentNoteBook(currentUser);
	}

	@Override
	public void createNewNote()
	{
		model = new Note();
		model.initialize();
		((NoteController)controller).setCurrentNote((Note)model);
		
		NoteContent content = new NoteContent();
		content.initialize();
		
		((Note)model).setContent(content);
		setCurrentNote((Note)model);
	}
}