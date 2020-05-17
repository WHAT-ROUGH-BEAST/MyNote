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
	void deleteContentFile(int id);
	void updateText(String text);
	void downloadFile();
	void playAudio();
	void setCurrentContent(NoteContent content);
}

public class Editor extends Controller implements EditorInterface
{
	// ��¼���е��ļ�id ɾ��ʱͨ��idȷ��model�и�ɾ��˭
	private ArrayList<Integer> IDs = new ArrayList<Integer>();
	
	public Editor(Model model, View view)
	{
		super(model, view);
		// model.initialize();
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

	// TODO : ��ͼ����¼�����
	@Override
	public void downloadFile()
	{
		// TODO Auto-generated method stub
		
	}
	
	// TODO : ��ͼ����¼�����
	@Override
	public void playAudio()
	{
		// TODO Auto-generated method stub
		
	}

	// TODO : ��ͼɾ���¼��Ĵ���
	@Override
	public void deleteContentFile(int id)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentContent(NoteContent content)
	{
		model = content;
		((NoteContent)model).setText(content.getText());
	}
}
