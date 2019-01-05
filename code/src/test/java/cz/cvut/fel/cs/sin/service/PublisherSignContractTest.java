package cz.cvut.fel.cs.sin.service;


import com.google.common.base.Preconditions;
import cz.cvut.fel.cs.sin.dao.AuthorDAOImpl;
import cz.cvut.fel.cs.sin.dao.PublisherDAOImpl;
import cz.cvut.fel.cs.sin.entity.Author;
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
public class PublisherSignContractTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Publisher.class.getPackage())
                .addPackage(PublisherDAOImpl.class.getPackage())
                .addPackage(PublisherServiceImpl.class.getPackage())
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
    AuthorDAOImpl daoAuthor;

    @Test
    public void signContractTest() {

        List<Publisher> publishers = daoPublisher.list();
        List<Author> authors = daoAuthor.list();

        Publisher publisher = publishers.get(0);
        Author author = authors.get(0);

        service.publisherSignContract(publisher.getPublisherId(), author.getAuthorId());

        publisher = daoPublisher.find(publisher.getPublisherId());
        author = daoAuthor.find(author.getAuthorId());

        Assert.assertFalse(publisher.getAuthors().isEmpty());
        Assert.assertFalse(author.getPublishers().isEmpty());


    }

}
