package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Library;

public interface LibraryService {

    public void register(Library library);

    public void libraryAddBook(Object libraryId, Object bookId);

}
