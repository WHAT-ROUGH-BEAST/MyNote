package Controller;

import java.util.*;

import Model.Model;
import Model.Note;
import Model.NoteBook;
import Model.User;
import View.View;

interface SignInInterface
{
	
}

interface AllBookControllerInterface
{
	void setCurrentUser(Model model);
	void updateNoteBook(NoteBook updatedNoteBook);
}

public class UserController extends Controller 
		implements AllBookControllerInterface, SignInInterface
{
	public UserController(Model model, View view)
	{
		super(model, view);
		// model.initialize();
	}

	@Override
	public void setCurrentUser(Model model)
	{
		this.model = model;
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
}
