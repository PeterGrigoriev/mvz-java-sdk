package com.movilizer.moveletbuilder;

import com.movilitas.movilizer.v15.MovilizerMovelet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XmlMoveletBuilderTest {
    private final IMoveletBuilder moveletBuilder;

    private static final String moveletXml = "                <MovilizerMovelet name=\"Hello\"\n" +
            "                                  validTillDate=\"20131231T23:59:00.000Z\"\n" +
            "                                  moveletType=\"MULTI\"\n" +
            "                                  initialQuestionKey=\"Question_One\"\n" +
            "                                  moveletKey=\"HelloWorld\">\n" +
            "\n" +
            "                    <question type=\"0\" title=\"Welcome to Movilizer\" key=\"Question_One\">\n" +
            "                        <answer position=\"1\" nextQuestionKey=\"END\" key=\"Question_One_A1\">\n" +
            "                            <text>This one is a simple message screen</text>\n" +
            "                        </answer>\n" +
            "                    </question>\n" +
            "\n" +
            "                </MovilizerMovelet>\n";


    public XmlMoveletBuilderTest() {
        this.moveletBuilder = new XmlMoveletBuilder(moveletXml);
    }

    @Test
    public void testBuildMovelet() throws Exception {
        List<MovilizerMovelet> movelets = moveletBuilder.buildMovelets();
        Assert.assertNotNull(movelets);
        Assert.assertFalse(movelets.isEmpty());

    }
}
