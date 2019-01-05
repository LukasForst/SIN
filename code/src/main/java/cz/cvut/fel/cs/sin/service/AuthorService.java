package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Author;
import cz.cvut.fel.cs.sin.entity.Book;

public interface AuthorService {
    void createAuthor(Author author);

    Author find(int id);
}
