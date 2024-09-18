package gr.symfoititis.common.services;

import gr.symfoititis.common.dao.NotesDao;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.records.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotesService {
    private final NotesDao notesDao;

    public NotesService (NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    /**
     *
     * Notes
     */
    public List<Note> getNotes (Integer c_id) {
        if (Objects.isNull(c_id) || c_id.compareTo(0) <= 0) {
            throw new BadRequestException("Bad Request");
        }
        return notesDao.getNotes(c_id);
    }

}
