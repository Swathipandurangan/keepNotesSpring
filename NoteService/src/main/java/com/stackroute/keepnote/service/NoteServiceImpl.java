package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	NoteRepository noteRepository;

	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;

	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(NoteUser noteUser) {

//		List<Note> noteList = new ArrayList<>(noteUser.getNotes().size());
//		for (Note n: noteUser.getNotes()) {
//			noteList.add(n);
//		}		

//		NoteUser noteUser = new NoteUser();
//		noteUser.setNotes(noteList);

		NoteUser createSuccess;
		createSuccess = this.noteRepository.insert(noteUser);
		if (createSuccess != null) {
			return true;
		} else {
			return false;
		}

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String userId, int noteId) {
		try {

			Optional<NoteUser> noteUserDetails = this.noteRepository.findById(userId);
			List<Note> listOfAllNotes = noteUserDetails.get().getNotes();
			NoteUser noteUser = new NoteUser();

			listOfAllNotes.forEach(noteDelete -> {
				if (noteDelete.getNoteId() == noteId) {
					// listOfAllNotes.add(noteDelete);
					noteUser.setNotes(listOfAllNotes);
					this.noteRepository.delete(noteUser);
				}
			});

			return true;

		} catch (Exception e) {
			throw new NullPointerException();
		}
	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {

		this.noteRepository.deleteById(userId);
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {

		try {
			Optional<NoteUser> userDetails = this.noteRepository.findById(userId);
			if (userDetails.isPresent()) {
				NoteUser noteUser = userDetails.get();

				List<Note> notes = noteUser.getNotes();
				notes.add(note);

				noteUser.setNotes(notes);
				this.noteRepository.save(noteUser);
				return note;
			} else {
				throw new NoteNotFoundExeption("Note Not Found");
			}
		} catch (Exception e) {
			throw new NoteNotFoundExeption("Note Not Found");
		}
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {

		Note noteFromDBByID = null;
		try {
			Optional<NoteUser> userDetails = this.noteRepository.findById(userId);
			if (userDetails.isPresent()) {
				List<Note> notes = userDetails.get().getNotes();
				Optional<Note> noteDetails = notes.stream().filter(note -> note.getNoteId() == noteId).findFirst();
				if (noteDetails.isPresent()) {
					noteFromDBByID = noteDetails.get();
				} else {
					throw new NoteNotFoundExeption("Note Not found");
				}
			} else
				throw new NoteNotFoundExeption("Note Not Found");
		} catch (Exception e) {
			throw new NoteNotFoundExeption("Note Not Found");
		}
		return noteFromDBByID;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {

		Optional<NoteUser> userDetailsByUserId = this.noteRepository.findById(userId);
		List<Note> listOfAllNotesForThatUser = userDetailsByUserId.get().getNotes();
		return listOfAllNotesForThatUser;
	}
}
