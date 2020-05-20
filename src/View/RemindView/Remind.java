package View.RemindView;

import Model.Note;

public class Remind
{
	private String bookName;
	private Note note;
	public Remind(Note note, String bookName)
	{
		this.note = note;
		this.bookName = bookName;
	}
	
	public String getBookName()
	{
		return bookName;
	}
	
	public Note getNote()
	{
		return note;
	}
	
	public void setNote(Note note)
	{
		note = note.clone();
	}
}
