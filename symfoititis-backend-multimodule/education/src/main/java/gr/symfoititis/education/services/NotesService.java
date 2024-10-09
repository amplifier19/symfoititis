package gr.symfoititis.education.services;

import gr.symfoititis.common.exceptions.InternalServerErrorException;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.education.dao.NotesDao;
import gr.symfoititis.education.records.Note;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotesService {
    private final NotesDao notesDao;
    public NotesService(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    public List<Note> getNotes (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return notesDao.getNotes(c_id);
    }

    public void addNote (Note note) {
        if (
                Objects.isNull(note) ||
                        Objects.isNull(note.c_id()) ||
                        note.c_id().compareTo(0) <= 0 ||
                        note.type().isBlank() ||
                        note.note_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            notesDao.addNote(note);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    public void updateNote (Note note) {
        if (
                Objects.isNull(note) ||
                        Objects.isNull(note.note_id()) ||
                        note.note_id().compareTo(0) <= 0 ||
                        Objects.isNull(note.c_id()) ||
                        note.c_id().compareTo(0) <= 0 ||
                        note.type().isBlank() ||
                        note.note_display_name().isBlank()
        ) {
            throw new BadRequestException("Bad Request");
        }
        try {
            int status = notesDao.updateNote(note);
            if (status == 0) {
                throw new InternalServerErrorException("Internal Server Error");
            }
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request");
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void deleteNote (Integer note_id) {
        if (Objects.isNull(note_id) || note_id.compareTo(0) <=0) {
            throw new BadRequestException("Bad Request");
        }
        int status = notesDao.deleteNote(note_id);
        if (status == 0) {
            throw new NotFoundException("Course Not Found");
        }
    }
}
