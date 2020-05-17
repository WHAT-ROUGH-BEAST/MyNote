package Controller;

import java.util.*;

import Model.Model;
import Model.Note;
import Model.NoteBook;
import View.View;

interface NoteListControllerInterface
{
	void addNote(Note note);
	void removeNote(int id);
	void setCurrentNoteBook(NoteBook noteBookChoosed);
}

public class NoteBookController extends Controller implements NoteListControllerInterface
{

	public NoteBookController(Model model, View view)
	{
		super(model, view);
		// model.initialize();
	}

	@Override
	public void addNote(Note note)
	{
		if (null == note)
			throw new RuntimeException();
		
		ArrayList<Note> notes = (ArrayList<Note>) ((NoteBook)model).getNotes();
		
		// 如果是完全相同的笔记则覆盖
		for (Note n : notes)
		{
			if (n.getId() == note.getId())
			{
				n = note;
				((NoteBook)model).setNotes(notes);
				return;
			}
		}
		
		notes.add(note);
		((NoteBook)model).setNotes(notes);
	}

	@Override
	public void removeNote(int id)
	{
		ArrayList<Note> notes = (ArrayList<Note>) ((NoteBook)model).getNotes();
		
		for (Note n : notes)
		{
			if (id == n.getId())
			{
				notes.remove(n);
				((NoteBook)model).setNotes(notes);
				return;
			}
		}
		
		throw new RuntimeException("can't find such note");
	}

	// TODO : 改变list
	public void setCurrentNoteBook(NoteBook noteBookChoosed)
	{
		model = noteBookChoosed;
	}
}
