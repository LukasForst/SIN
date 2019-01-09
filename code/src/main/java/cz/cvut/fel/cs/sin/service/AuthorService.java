package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Author;

public interface AuthorService {
    void add(Author author);

    Author find(int id);
}
