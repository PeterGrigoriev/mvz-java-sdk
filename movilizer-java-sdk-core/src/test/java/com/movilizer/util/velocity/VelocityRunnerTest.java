package com.movilizer.util.velocity;

import com.movilizer.moveletbuilder.MoveletDataProviderBase;
import org.testng.annotations.Test;

import java.util.Date;

import static com.movilizer.util.datetime.Dates.afterOneMonth;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class VelocityRunnerTest {

    private final VelocityRunner runner;

    public VelocityRunnerTest() throws Exception {
        runner = VelocityRunner.newInstance(new DefaultMoveletTemplateTextLoader(),
                "WelcomeMovelet.vm");
    }

    @Test
    public void testNewInstance() throws Exception {
        assertNotNull(runner);
    }

    @Test
    public void testMerge() throws Exception {
        runner.put("context", new MoveletDataProviderBase() {

            public String getMoveletName() {
                return "Test Movelet";
            }


            public String getMoveletKey() {
                return "SomeMoveletKey";
            }

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

        });
        String result = runner.merge();
        assertNotNull(result);
        assertTrue(result.contains("SomeMoveletKeyExtension"));
    }
}
