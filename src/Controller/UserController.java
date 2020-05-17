package Controller;

import java.util.*;

import Model.Model;
import Model.NoteBook;
import Model.User;
import View.View;

interface SignInInterface
{
	
}

interface AllBookControllerInterface
{
	void setCurrentNoteBook(User currentUser);
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
	public void setCurrentNoteBook(User currentUser)
	{
		model = currentUser;
	}
}
