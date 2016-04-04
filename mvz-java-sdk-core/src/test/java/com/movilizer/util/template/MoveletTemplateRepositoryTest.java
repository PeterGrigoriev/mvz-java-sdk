package com.movilizer.util.template;

import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.moveletbuilder.MoveletDataProviderBase;
import com.movilizer.util.velocity.DefaultMoveletTemplateTextLoader;
import org.testng.annotations.Test;

import java.util.Date;

import static com.movilizer.util.datetime.Dates.afterOneMonth;
import static org.testng.Assert.assertNotNull;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletTemplateRepositoryTest {
    private final XmlTemplateRepository repository;

    public MoveletTemplateRepositoryTest() {
        repository = new XmlTemplateRepository("SomeProject", new DefaultMoveletTemplateTextLoader());
    }

    IMoveletDataProvider dataProvider = new MoveletDataProviderBase() {
        @Override
        public String getMoveletKeyExtension() {
            return "SomeMoveletKeyExtension";
        }

        @Override
        public String getNamespace() {
            return "";
        }

        @Override
        public Date getValidTillDate() {
            return afterOneMonth();
        }

    };

    @Test
    public void testGetTemplate() throws Exception {
        IXmlTemplate template = repository.getTemplate("WelcomeMovelet.vm");
        assertNotNull(template);
    }
}
