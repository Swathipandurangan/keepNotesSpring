package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;

import java.util.List;


public interface NoteService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */


    boolean createNote(NoteUser note);

    boolean deleteNote(String userId, int noteId);

    boolean deleteAllNotes(String userId) throws NoteNotFoundExeption;

    Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption;

    Note getNoteByNoteId(String userId,int noteId) throws NoteNotFoundExeption;

    List<Note> getAllNoteByUserId(String userId);


}
