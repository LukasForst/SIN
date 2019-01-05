package cz.cvut.fel.cs.sin.service;


import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.util.Resource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class BookAddTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Book.class.getPackage())
                .addPackage(BookDAOImpl.class.getPackage())
                .addPackage(BookServiceImpl.class.getPackage())
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    BookService creation;

    @Inject
    BookDAOImpl dao;

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book Testing");
        book.setDatePublished("Test");
        book.setGenre("Test");
        book.setISBN("Test");
        {
            List<Book> books = dao.list();
            books.forEach(b -> System.out.println(b.getTitle()));
            Assert.assertEquals(books.size(), 2);
        }
        creation.createBook(book);
        {
            List<Book> books = dao.list();
            books.forEach(b -> System.out.println(b.getTitle()));
            Assert.assertEquals(books.size(), 3);
        }


    }

}
