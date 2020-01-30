package com.example.demo;

import org.springframework.stereotype.Component;
import com.example.demo.api.viewmodel.NoteViewModel;
import com.example.demo.api.viewmodel.NotebookViewModel;
import com.example.demo.db.NotebookRepository;
import com.example.demo.model.Note;
import com.example.demo.model.Notebook;

import java.util.UUID;

/**
 * Componente que maneja todos los mapeos del proyecto
 * - entidad a view model
 * - view model a entidad
 * Todo el mapeo se produce aquí, pero en producción no es la mejor
 * práctica. Echar un vistazo al proyecto ModelMapper o al menos 
 * dividir el mapeo.
 */

@Component
public class Mapper {
    private NotebookRepository notebookRepository;

    public Mapper(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public NoteViewModel convertToNoteViewModel(Note entity) {
    	NoteViewModel viewModel = new NoteViewModel();
        viewModel.setTitle(entity.getTitle());
        viewModel.setId(entity.getId().toString());
        viewModel.setLastModifiedOn(entity.getLastModifiedOn());
        viewModel.setText(entity.getText());
        viewModel.setNotebookId(entity.getNotebook().getId().toString());

        return viewModel;
    }

    public Note convertToNoteEntity(NoteViewModel viewModel) {
    	Notebook notebook = this.notebookRepository.findById(UUID.fromString(viewModel.getNotebookId())).get();
    	Note entity = new Note(viewModel.getId(), viewModel.getTitle(), viewModel.getText(), notebook);

        return entity;
    }

    public NotebookViewModel convertToNotebookViewModel(Notebook entity) {
    	NotebookViewModel viewModel = new NotebookViewModel();
        viewModel.setId(entity.getId().toString());
        viewModel.setName(entity.getName());
        viewModel.setNbNotes(entity.getNotes().size());

        return viewModel;
    }

    public Notebook convertToNotebookEntity(NotebookViewModel viewModel) {
    	Notebook entity = new Notebook(viewModel.getId(), viewModel.getName());

        return entity;
    }

}