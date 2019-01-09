package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class BookServiceImpl implements BookService {

    @Inject
    private BookDAOImpl bookDAO;

    @Inject
    Logger logger;

    public void add(Book book) {
        bookDAO.save(book);
        logger.info("Created book with title: " + book.getTitle() + ", id: " + book.getBookId());
    }

}
