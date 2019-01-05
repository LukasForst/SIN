package cz.cvut.fel.cs.sin.service;

import com.google.common.base.Preconditions;
import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.LibraryDAOImpl;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.entity.Library;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class LibraryServiceImpl implements LibraryService {

    @Inject
    private LibraryDAOImpl libraryDAO;

    @Inject
    private BookDAOImpl bookDAO;

    @Inject
    Logger logger;

    public void register(Library library) {
        libraryDAO.save(library);
        logger.info("Created library with name: " + library.getName() + ", id: " + library.getLibraryId());
    }

    @Override
    public void libraryAddBook(Object libraryId, Object bookId) {
        Library library = Preconditions.checkNotNull(libraryDAO.find(libraryId));
        Book book = Preconditions.checkNotNull(bookDAO.find(bookId));

        library.getOwnBooks().add(book);
        libraryDAO.update(library);

        logger.info("Added book " + book.getTitle() + " to library " + library.getName());

    }
}
