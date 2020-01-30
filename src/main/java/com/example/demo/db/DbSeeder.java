package com.example.demo.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.example.demo.model.Notebook;
import com.example.demo.model.Note;

/**
 * Este componente SOLO se ejecutará y será instanciado si la
 * propiedad demo.db.recreate está a true en el archivo
 * application.properties
 */

@Component
@ConditionalOnProperty(name = "demo.db.recreate", havingValue = "true")
public class DbSeeder implements CommandLineRunner {
    private NotebookRepository notebookRepository;
    private NoteRepository noteRepository;

    public DbSeeder(NotebookRepository notebookRepository,
                    NoteRepository noteRepository) {
        this.notebookRepository = notebookRepository;
        this.noteRepository = noteRepository;
    }


    @Override
    public void run(String... args) {
        // borra todas las entidades existentes
        this.notebookRepository.deleteAll();
        this.noteRepository.deleteAll();

        this.notebookRepository.save(new Notebook("Default"));

        this.notebookRepository.save(new Notebook("Quotes"));

        this.noteRepository.save(new Note("Hello", "Welcome to Note It",
        				new Notebook("Default")
        				));

        System.out.println("Initialized database");
    }

}
