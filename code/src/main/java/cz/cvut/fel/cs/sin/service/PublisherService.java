package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Publisher;

public interface PublisherService {
    void add(Publisher publisher);

    void singContract(int publisherId, int authorId);

    void publishBook(int publisherId, int bookId);
}
