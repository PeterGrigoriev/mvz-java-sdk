package com.movilizer.masterdata;

import com.movilitas.movilizer.v14.MovilizerMasterdataPoolUpdate;
import com.movilizer.util.template.ResourceXmlTemplateRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MasterdataUpdateBuilderTest {


    private MasterdataUpdateBuilder masterdataUpdateBuilder;
    private MyMasterdataUpdateInfoProvider infoProvider;

    @BeforeMethod
    public void setUp() throws Exception {
        masterdataUpdateBuilder = new MasterdataUpdateBuilder(new ResourceXmlTemplateRepository("Test", getClass()));
        infoProvider = new MyMasterdataUpdateInfoProvider();
    }

    @Test
    public void testBuild() throws Exception {
        MovilizerMasterdataPoolUpdate update = masterdataUpdateBuilder.build(infoProvider, "/com/movilizer/masterdata/masterdata-update-test.vm");
        Assert.assertEquals(update.getPool(), infoProvider.getMyPool());
        Assert.assertEquals(update.getUpdate().size(), 1);
        Assert.assertEquals(update.getUpdate().get(0).getDescription(), infoProvider.getMyField());
    }


    public static class MyMasterdataUpdateInfoProvider implements IMasterdataUpdateInfoProvider {
        @Override
        public int getAcknowledgementKey() {
            return 200;
        }

        @Override
        public String getObjectKey() {
            return "101";
        }

        public String getMyField() {
            return "This is my field";
        }

        public String getMyPool() {
            return "myPool";
        }
    }
}