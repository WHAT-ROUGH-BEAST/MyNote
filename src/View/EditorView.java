package View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import Controller.Editor;
import Model.Model;
import Model.NoteContent;
import Controller.IOOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import netscape.javascript.JSException;

interface EditorViewInterface
{
	NoteContent updateContent(boolean clear);
	void setCurrentContent(NoteContent content);
}

public class EditorView extends View implements EditorViewInterface
{
	@FXML private HTMLEditor htmlEditor;
	private WebEngine engine;
	
	private Button 
			ImgButton = new Button("Img"), 
			AttachButton = new Button("Attach"), 
			AudioButton = new Button("Audio");
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// mvc
		model = new NoteContent();
		model.initialize();
		controller = new Editor(model, this);
		
		model.addPropertyChangeListener(this);

		// 加载htmleditor编辑工具
		initWebEngine();
		
		// add pic audio attachment buttons
		addExtraButton();
		ImgButton.setOnAction((event) -> onBtnAction("Img"));
		AttachButton.setOnAction((event) -> onBtnAction("Attach"));
		AudioButton.setOnAction((event) -> onBtnAction("Audio"));
	}
	
	// 响应被观察者model中的改变
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		htmlEditor.setHtmlText((String)evt.getNewValue());
	}
	
	// 给noteview一个接口
	@Override
	public NoteContent updateContent(boolean clear)
	{
		// 点击确认按钮之后，更新内容
		if (clear)
			((Editor)controller).updateText("");
		else
			((Editor)controller).updateText(htmlEditor.getHtmlText());
		
		return (NoteContent) this.model;
	}
	
	@Override
	public void setCurrentContent(NoteContent content)
	{
		model.removePropertyChangeListener(this);
		model = content.clone();
		model.addPropertyChangeListener(this);
		
		((Editor)controller).setCurrentContent(model);
	}
	
	private void addExtraButton()
	{
		Node node = htmlEditor.lookup(".top-toolbar");
		if (!(node instanceof ToolBar))
			throw new RuntimeException();
		
		ToolBar bar = (ToolBar) node;
		bar.getItems().add(ImgButton);
		bar.getItems().add(AttachButton);
		bar.getItems().add(AudioButton);
		bar.getItems().add(new Separator(Orientation.VERTICAL));
	}
	
	private void onBtnAction(String actionType)
	{
		try
		{
        	String base64data = IOOperator.importDataFile(
        			IOOperator.ChooseFile(htmlEditor.getScene().getWindow(), actionType));        	
			switch(actionType)
			{
				case "Img":
					// TODO : insert text after cursor!!
					// insertAfterCursor(htmlImgHead + base64data + htmlImgTail);
					break;
				case "Attach":
					((Editor)controller).addAttach(base64data);
					base64data = urlToPic("file");
					break;
				case "Audio":
					((Editor)controller).addAudio(base64data);
					base64data = urlToPic("audio");
					break;
				default:
					break;
			}
			
			// 通过controller更新model
			// TODO : 再加一个位置，应该是点击确认按钮之后才更
			((Editor)controller).updateText(htmlEditor.getHtmlText() 
					+ htmlExcecutor.htmlImgHead 
					+ base64data 
					+ htmlExcecutor.htmlImgTail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}   
	}

	// 取得resource中的图片
	private String urlToPic(String picName) throws Exception
	{
		URL url = getClass().getClassLoader().getResource("image/" + picName + ".png");
		String base64pic = IOOperator.getResourceImg(url);
		return base64pic;
	}
	
	// 加载htmleditor编辑工具
	private void initWebEngine()
	{
		Node webNode = htmlEditor.lookup(".web-view");
		if (!(webNode instanceof WebView)) 
			throw new RuntimeException();
		WebView webView = (WebView) webNode;
		engine = webView.getEngine();
	}
	
	// TODO : 实现在光标后添加图片
	private void insertAfterCursor(String txt)
	{
        try {
            engine.executeScript(htmlExcecutor.jsCodeInsertHtml.
            		replace("####html####",
            				htmlExcecutor.escapeJavaStyleString(txt, true, true)));
        } catch (JSException e) {
            e.printStackTrace();
        }
	}
}

class htmlExcecutor
{
	public static String 
	htmlImgHead = "<p><img src=\"data:image/;base64, ",
	htmlImgTail = "\"/></p>";
	
	static String jsCodeInsertHtml = 
			"function insertHtmlAtCursor(html) {\n"
			        + "    var range, node;\n"
			        + "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
			        + "        range = window.getSelection().getRangeAt(0);\n"
			        + "        node = range.createContextualFragment(html);\n"
			        + "        range.insertNode(node);\n"
			        + "    } else if (document.selection && document.selection.createRange) {\n"
			        + "        document.selection.createRange().pasteHTML(html);\n"
			        + "    }\n"
			        + "}insertHtmlAtCursor('####html####')";
	
    private static String hex(int i) {
        return Integer.toHexString(i);
    }
    
	static String escapeJavaStyleString(String str,
            boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }
}
