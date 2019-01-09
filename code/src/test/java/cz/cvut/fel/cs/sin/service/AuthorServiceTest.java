package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;
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
public class AuthorServiceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Author.class.getPackage())
                .addPackage(AuthorDAOImpl.class.getPackage())
                .addPackage(AuthorServiceImpl.class.getPackage())
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    AuthorService service;

    @Inject
    AuthorDAOImpl dao;

    @Test
    public void testAddAuthor() {
        Author author = new Author();
        author.setLastName("Navara");
        author.setName("Mirko");
        author.setEmail("navara@cmp.cvut.cz");

        List<Author> authors = dao.list();
        authors.forEach(a -> System.out.println(a.getName() + " " + a.getLastName()));
        Assert.assertEquals(authors.size(), 3);

        service.add(author);
        authors = dao.list();
        authors.forEach(a -> System.out.println(a.getName() + " " + a.getLastName()));
        Assert.assertEquals(authors.size(), 4);


    }

}
