package cz.cvut.fel.cs.sin.service;


import cz.cvut.fel.cs.sin.dao.BookDAOImpl;
import cz.cvut.fel.cs.sin.dao.PublisherDAOImpl;
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
public class PublisherAddTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Publisher.class.getPackage())
                .addPackage(PublisherDAOImpl.class.getPackage())
                .addPackage(PublisherServiceImpl.class.getPackage())
                .addClass(Resource.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    PublisherService creation;

    @Inject
    PublisherDAOImpl dao;

    @Test
    public void testAddBook() {
        Publisher publisher = new Publisher();
        publisher.setAddress("Test Publisher Address");
        publisher.setName("Test Publisher");
        {
            List<Publisher> publishers = dao.list();
            publishers.forEach(b -> System.out.println(b.getName()));
            Assert.assertEquals(publishers.size(), 2);
        }
        creation.register(publisher);
        {
            List<Publisher> publishers = dao.list();
            publishers.forEach(b -> System.out.println(b.getName()));
            Assert.assertEquals(publishers.size(), 3);
        }


    }

}
