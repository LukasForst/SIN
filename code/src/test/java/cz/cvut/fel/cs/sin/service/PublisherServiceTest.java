package cz.cvut.fel.cs.sin.service;

import com.google.common.base.Preconditions;
import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.PublisherDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.entity.Publisher;
import cz.cvut.fel.cs.sin.util.Resource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
public class PublisherServiceTest {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Publisher.class.getPackage())
                .addPackage(PublisherDAOImpl.class.getPackage())
                .addPackage(PublisherService.class.getPackage())
                .addPackage(PublisherServiceImpl.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookDAOImpl.class.getPackage())
                .addPackage(Author.class.getPackage())
                .addPackage(AuthorDAOImpl.class.getPackage())
                .addClass(Preconditions.class)
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    PublisherService service;

    @Inject
    PublisherDAOImpl daoPublisher;

    @Inject
    BookDAOImpl daoBook;

    @Inject
    AuthorDAOImpl daoAuthor;

    @Test
    public void testAddBook() {
        Publisher publisher = new Publisher();
        publisher.setAddress("Test Publisher Address");
        publisher.setName("Test Publisher");
        List<Publisher> publishers = daoPublisher.list();
        publishers.forEach(b -> System.out.println(b.getName()));
        Assert.assertEquals(publishers.size(), 2);
        service.add(publisher);
        publishers = daoPublisher.list();
        publishers.forEach(b -> System.out.println(b.getName()));
        Assert.assertEquals(publishers.size(), 3);


    }

    @Test
    public void registerAuthor() {

        List<Publisher> publishers = daoPublisher.list();
        List<Book> books = daoBook.list();

        Publisher publisher = publishers.get(0);
        Book book = books.get(0);

        service.publishBook(publisher.getPublisherId(), book.getBookId());
        publisher = daoPublisher.find(publisher.getPublisherId());

        Assert.assertFalse(publisher.getPublishedBooks().isEmpty());

    }

    @Test
    public void signContractTest() {

        List<Publisher> publishers = daoPublisher.list();
        List<Author> authors = daoAuthor.list();

        Publisher publisher = publishers.get(0);
        Author author = authors.get(0);

        service.singContract(publisher.getPublisherId(), author.getAuthorId());

        publisher = daoPublisher.find(publisher.getPublisherId());
        author = daoAuthor.find(author.getAuthorId());

        Assert.assertFalse(publisher.getAuthors().isEmpty());
        Assert.assertFalse(author.getPublishers().isEmpty());

    }
}
