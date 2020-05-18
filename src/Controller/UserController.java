package Controller;

import java.util.*;

import Model.Model;
import Model.Note;
import Model.NoteBook;
import Model.User;
import View.View;

interface MainViewInterface
{
	void addNoteBook(String noteBookName);
	void setCurrentUser(Model model);
}

interface AllBookControllerInterface
{
	void setCurrentUser(Model model);
	void updateNoteBook(NoteBook updatedNoteBook);
	void setNoteBookNameListener(View listener);
}

public class UserController extends Controller 
		implements AllBookControllerInterface, MainViewInterface
{
	private View noteBookNameListener = null;
	
	public UserController(Model model, View view)
	{
		super(model, view);
		// model.initialize();
	}

	@Override
	public void setCurrentUser(Model model)
	{
		this.model = model;
		
		// Ã·–—noteview
		notifynoteBookNameListener();
		
		((User)model).setAccount(((User)model).getAccount());
		((User)model).setNoteBooks(((User)model).getNoteBooks());
		((User)model).setPassword(((User)model).getPassword());
	}

	@Override
	public void updateNoteBook(NoteBook updatedNoteBook)
	{
		ArrayList<NoteBook> noteBooks = ((User)model).getNoteBooks();
		for (NoteBook book : noteBooks)
		{
			if (book.getName().equals(updatedNoteBook.getName()))
			{
				noteBooks.remove(book);
				noteBooks.add(updatedNoteBook);
				break;
			}
		}
		((User)model).setNoteBooks(noteBooks);
	}

	@Override
	public void addNoteBook(String noteBookName)
	{		
		if (null == noteBookName || noteBookName.isBlank() || noteBookName.isEmpty())
			throw new RuntimeException("invalide NoteBook name : empty input");
		for (NoteBook book : ((User)model).getNoteBooks())
		{
			if (book.getName().equals(noteBookName))
				throw new RuntimeException("invalide NoteBook name : repeated NoteBook name");
		}
		
		ArrayList<NoteBook> books = ((User)model).getNoteBooks();
		NoteBook book = new NoteBook();
		book.initialize();
		book.setName(noteBookName);
		books.add(book);
		((User)model).setNoteBooks(books);
	}

	@Override
	public void setNoteBookNameListener(View listener)
	{
		noteBookNameListener = listener;
	}
	
	private void notifynoteBookNameListener()
	{
		model.addPropertyChangeListener(noteBookNameListener);
	}
}
