package Controller;

import java.util.*;

import Model.Model;
import Model.Note;
import Model.NoteContent;
import View.View;

interface NoteControllerInterface
{
	// 更新model
	void updateNote(String title, String labels, String date, NoteContent noteContent);
	void setCurrentNote(Note currentNote);
}

public class NoteController extends Controller implements NoteControllerInterface
{
	public NoteController(Model model, View view)
	{
		super(model, view);
		// model.initialize();
	}

	@Override
	public void updateNote(String title, String labels, String date, NoteContent noteContent)
	{
		//title
		if (null == title)
			title = "";
		
		// label
		ArrayList<String> label;
		if (null == labels || labels.equals(""))
			label = null;
		else
			label = new ArrayList<String>(Arrays.asList(labels.split(",")));
		
		// date
		Calendar cal;
		System.out.println(date.split("/").length);
		if (null == date || date.equals(""))
			cal = null;
		else
		{
			String[] dates = date.split("/");
			cal = Calendar.getInstance();
			cal.set(Integer.parseInt(dates[0].trim()), 
					Integer.parseInt(dates[1].trim())-1,
					Integer.parseInt(dates[2].trim()));
		}
		
		((Note)model).setTitle(title);
		((Note)model).setLabels(label);
		((Note)model).setAlert(cal);
		((Note)model).setContent(noteContent);
	}

	@Override
	public void setCurrentNote(Note currentNote)
	{
		// 问题所在
		model = currentNote;
		((Note)model).setAlert(currentNote.getAlert());
		((Note)model).setLabels(currentNote.getLabels());
		((Note)model).setTitle(currentNote.getTitle());
	}
}
