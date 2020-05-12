package Controller;

import java.util.*;

import Model.Model;
import Model.NoteContent;
import View.EditorView;
import View.View;
import javafx.scene.web.WebEngine;

interface EditorInterface
{
	void addAttach(String base64data);
	void addAudio(String base64data);
	void updateText(String text);
	void downloadFile();
	void playAudio();
}

public class Editor extends Controller implements EditorInterface
{
	// 记录所有的文件id 删除时通过id确定model中该删除谁
	private ArrayList<Integer> IDs = new ArrayList<Integer>();
	
	public Editor(Model model, View view)
	{
		super(model, view);
	}

	@Override
	public void updateText(String text)
	{
		((NoteContent)model).setText(text);
	}

	@Override
	public void addAttach(String base64data)
	{
		int id = ((NoteContent)model).addAttachs(base64data);
		IDs.add(id);
	}

	@Override
	public void addAudio(String base64data)
	{
		int id = ((NoteContent)model).addAttachs(base64data);
		IDs.add(id);
	}

	@Override
	public void downloadFile()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playAudio()
	{
		// TODO Auto-generated method stub
		
	}
}
