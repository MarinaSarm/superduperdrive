package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer addNote(Note note) {
        Note newNote = new Note();
        newNote.setUserId(note.getUserId());
        newNote.setNoteTitle(note.getNoteTitle());
        newNote.setNoteDescription(note.getNoteDescription());
        return noteMapper.insert(note);
    }

    public void deleteNote(Integer noteid) {
        noteMapper.deleteNote(noteid);
    }

    public void updateNote(Note newNote) {
        noteMapper.updateNote(newNote);
    }

    public List<Note> getNotes() {
        return noteMapper.getAllNotes();
    }
}
