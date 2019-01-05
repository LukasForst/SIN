package cz.cvut.fel.cs.sin.service;

import cz.cvut.fel.cs.sin.dao.LibraryDAOImpl;
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
public class LibraryAddTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Library.class.getPackage())
                .addPackage(LibraryDAOImpl.class.getPackage())
                .addPackage(LibraryServiceImpl.class.getPackage())
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    LibraryService creation;

    @Inject
    LibraryDAOImpl dao;

    @Test
    public void testAddLibrary() {
        Library library = new Library();
        library.setName("Nova knihovna");
        {
            List<Library> libraries = dao.list();
            libraries.forEach(b -> System.out.println(b.getName()));
            Assert.assertEquals(libraries.size(), 1);
        }
        creation.register(library);
        {
            List<Library> libraries = dao.list();
            libraries.forEach(b -> System.out.println(b.getName()));
            Assert.assertEquals(libraries.size(), 2);
        }


    }

}
