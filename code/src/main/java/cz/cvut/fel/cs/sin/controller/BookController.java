package cz.cvut.fel.cs.sin.controller;


import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.LibraryDAOImpl;
import cz.cvut.fel.cs.sin.dao.PublisherDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.entity.Library;
import cz.cvut.fel.cs.sin.entity.Publisher;
import cz.cvut.fel.cs.sin.service.AuthorService;
import cz.cvut.fel.cs.sin.service.BookService;
import cz.cvut.fel.cs.sin.service.LibraryService;
import cz.cvut.fel.cs.sin.service.PublisherService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Model
public class BookController {

    @Inject
    Logger logger;

    @Inject
    private BookService bookService;

    @Inject
    private PublisherService publisherService;

    @Inject
    private LibraryService libraryService;

    @Inject
    private AuthorService authorService;

    @Inject
    private BookDAOImpl bookDAO;

    @Inject
    private LibraryDAOImpl libraryDAO;

    @Inject
    private PublisherDAOImpl publisherDAO;

    @Inject
    private AuthorDAOImpl authorDAO;

    @Inject
    private FacesContext facesContext;

    @Named
    @Produces
    private Book book;

    @Produces
    @Named
    private Book newBook;

    @Produces
    @Named
    private String newBookAuthorId;

    public String getNewBookAuthorId() {
        return newBookAuthorId;
    }

    public void setNewBookAuthorId(String value) {
        this.newBookAuthorId = value;
    }

    @Named
    @Produces
    private List<Book> books;

    @Named
    @Produces
    private List<Author> authors;

    @Named
    @Produces
    private Author selectedAuthor;


    @Named
    @Produces
    private List<Publisher> publishers;

    @Named
    @Produces
    private List<Library> libraries;

    @Named
    @Produces
    private Author APAuthor;

    @Produces
    @Named
    private Author newAuthor;

    @Named
    @Produces
    private Publisher APPublisher;

    @Named
    @Produces
    private Publisher PBPublisher;

    @Named
    @Produces
    private Book PBBook;

    @Named
    @Produces
    private Book BLBook;

    @Named
    @Produces
    private Library BLLibrary;


    @PostConstruct
    public void initNewBook() {
        if (logger != null) logger.info("Recreating!");

        newBook = new Book();
        newBookAuthorId = "";
        newAuthor = new Author();
        selectedAuthor = new Author();

        APPublisher = new Publisher();
        APAuthor = new Author();
        PBPublisher = new Publisher();
        PBBook = new Book();
        BLBook = new Book();
        BLLibrary = new Library();

        if (bookDAO != null) books = bookDAO.list();
        if (libraryDAO != null) libraries = libraryDAO.list();
        if (publisherDAO != null) publishers = publisherDAO.list();
        if (authorDAO != null) authors = authorDAO.list();
    }

    public void APMakeContract() {
        logger.info("APMake!");
        publisherService.singContract(APPublisher.getPublisherId(), APAuthor.getAuthorId());
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contract signed!", "Contract signed");
        facesContext.addMessage(null, m);
        initNewBook();
    }

    public void PBAdd() {
        logger.info("PBADD");
        publisherService.publishBook(PBPublisher.getPublisherId(), PBBook.getBookId());
        initNewBook();
    }

    public void BLAdd() {
        logger.info("BLADD");
        libraryService.addBook(BLLibrary.getLibraryId(), BLBook.getBookId());
        initNewBook();
    }

    public void addBok() {
        Set<Author> s = new HashSet<>();
        s.add(authorService.find(Integer.valueOf(newBookAuthorId)));
        logger.info("Adding book " + newBook.getTitle());
        newBook.setAuthors(s);
        bookService.add(newBook);
        initNewBook();
    }

    public void addAuthor() {
        logger.info("Adding author " + newAuthor.getName());
        authorService.add(newAuthor);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Author added!", "Author added!");
        facesContext.addMessage(null, m);
        initNewBook();
    }
}
