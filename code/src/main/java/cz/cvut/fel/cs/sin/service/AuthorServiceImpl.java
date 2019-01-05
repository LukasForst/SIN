package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class AuthorServiceImpl implements AuthorService {

    @Inject
    private AuthorDAOImpl authorDAO;

    @Inject
    Logger logger;

    public void createAuthor(Author author) {
        authorDAO.save(author);
        logger.info("Created author with name: " + author.getName() + " " + author.getLastName() + ", id: " + author.getAuthorId());
    }

    @Override
    public Author find(int id) {
        return authorDAO.find(id);
    }
}
