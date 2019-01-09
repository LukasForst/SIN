package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Library;

public interface LibraryService {

    void add(Library library);

    void addBook(int libraryId, int bookId);

}
