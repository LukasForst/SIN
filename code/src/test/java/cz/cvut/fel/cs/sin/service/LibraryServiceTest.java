package cz.cvut.fel.cs.sin.service;


import com.google.common.base.Preconditions;
import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.LibraryDAOImpl;
import cz.cvut.fel.cs.sin.entity.Book;
import cz.cvut.fel.cs.sin.entity.Library;
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
public class LibraryServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Library.class.getPackage())
                .addPackage(LibraryDAOImpl.class.getPackage())
                .addPackage(LibraryService.class.getPackage())
                .addPackage(LibraryServiceImpl.class.getPackage())
                .addPackage(Book.class.getPackage())
                .addPackage(BookDAOImpl.class.getPackage())
                .addClass(Preconditions.class)
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    LibraryService service;

    @Inject
    LibraryDAOImpl daoLibrary;

    @Inject
    BookDAOImpl daoBook;

    @Test
    public void registerAuthor() {

        List<Library> publishers = daoLibrary.list();
        List<Book> books = daoBook.list();

        Library library = publishers.get(0);
        Book book = books.get(0);

        service.addBook(library.getLibraryId(), book.getBookId());
        library = daoLibrary.find(library.getLibraryId());

        Assert.assertFalse(library.getOwnBooks().isEmpty());

    }

    @Test
    public void testAddLibrary() {
        Library library = new Library();
        library.setName("Nova knihovna");
        List<Library> libraries = daoLibrary.list();
        libraries.forEach(b -> System.out.println(b.getName()));
        Assert.assertEquals(libraries.size(), 1);
        service.add(library);
        libraries = daoLibrary.list();
        libraries.forEach(b -> System.out.println(b.getName()));
        Assert.assertEquals(libraries.size(), 2);


    }

}
