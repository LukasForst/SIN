package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.entity.Publisher;

public interface PublisherService {
    public void register(Publisher publisher);

    public void publisherSignContract(Object publisherId, Object authorId);

    public void publisherPublishBook(Object publisherId, Object bookId);
}
