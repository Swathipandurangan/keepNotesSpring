package com.stackroute.keepnote.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 * 
 * @CrossOrigin, @EnableFeignClients and @RibbonClient needs to be added 
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class NoteController {
	
	private static final Logger LOG = Logger.getLogger(NoteController.class.getName()); 

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */

	@Autowired
	NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */
	@PostMapping("/note")
	public ResponseEntity<NoteUser> createNoteFromDB(@RequestBody NoteUser note) {
		LOG.log(Level.INFO, "create Note::: "+note); 
		try {

			boolean createSuccess = noteService.createNote(note);
			if (createSuccess) {
				return new ResponseEntity<>(note, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(note, HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid noteId without {}
	 */
	@DeleteMapping("/note/{userId}/{id}")
	public ResponseEntity<Note> deleteNoteFromDB(@PathVariable("userId") String userId,
			@PathVariable("id") int noteId) {
		boolean success = false;
		try {
			success = this.noteService.deleteNote(userId, noteId);
			if (success) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/note/{userId}")
	public ResponseEntity<String> deleteAllNotesByUserId(@PathVariable("userId") String userId) {

		boolean success = false;
		try {
			success = this.noteService.deleteAllNotes(userId);
			if (success) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT
	 * method.
	 */
	@PutMapping("/note/{userId}/{noteId}")
	public ResponseEntity<String> updateNoteFromDB(@RequestBody Note note, @PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {
		try {

			Note updatedNote = this.noteService.updateNote(note, noteId, userId);
			if (updatedNote != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET
	 * method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/note/{userId}")
	public ResponseEntity<String> getAllNotesById(@PathVariable("userId") String userId) {

		List<Note> notes = this.noteService.getAllNoteByUserId(userId);
		if (notes != null) {
			return new ResponseEntity(notes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/note/{userId}/{noteId}")
	public ResponseEntity<String> getNoteForSpecificUser(@PathVariable("userId") String userId, @PathVariable("noteId") int noteId) {
			
			Note notes;
			try {
				notes = this.noteService.getNoteByNoteId(userId, noteId);
				if (notes != null) {
					return new ResponseEntity(notes, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} catch (NoteNotFoundExeption e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}

}
