package cz.cvut.fel.cs.sin.service;

import com.google.common.base.Preconditions;
import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.PublisherDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.entity.Publisher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class PublisherServiceImpl implements PublisherService {

    @Inject
    private PublisherDAOImpl publisherDAO;

    @Inject
    private BookDAOImpl bookDAO;

    @Inject
    private AuthorDAOImpl authorDAO;

    @Inject
    Logger logger;

    public void add(Publisher publisher) {
        publisherDAO.save(publisher);
        logger.info("Created publisher with name: " + publisher.getName() + ", id: " + publisher.getPublisherId());
    }


    @Override
    public void publishBook(int publisherId, int bookId) {
        Publisher publisher = Preconditions.checkNotNull(publisherDAO.find(publisherId));
        Book book = Preconditions.checkNotNull(bookDAO.find(bookId));

        publisher.getPublishedBooks().add(book);
        publisherDAO.update(publisher);

        logger.info("Linked book: " + book.getTitle() + " to publisher " + publisher.getName());
    }

    @Override
    public void singContract(int publisherId, int authorId) {
        Publisher publisher = Preconditions.checkNotNull(publisherDAO.find(publisherId));
        Author author = Preconditions.checkNotNull(authorDAO.find(authorId));

        publisher.getAuthors().add(author);
        publisherDAO.update(publisher);

        logger.info("Linked author: " + author.getName() + " " + author.getLastName() + " to publisher " + publisher.getName());
    }


}
