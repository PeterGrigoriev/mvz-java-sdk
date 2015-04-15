package com.movilizer.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.movilitas.movilizer.v12.MovilizerReplyMovelet;
import com.movilizer.reply.converter.ReplyMoveletToJsonConverter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;


import static com.movilizer.util.resource.ResourceUtils.fromResource;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class ReplyMoveletToJsonConverterTest {

    private MovilizerReplyMovelet replyMovelet;
    private ReplyMoveletToJsonConverter converter;
    private JsonObject expectedJsonObject;

    @BeforeMethod
    public void setUp() throws Exception {

        replyMovelet = fromResource("/reports/rt-reply-movelet.xml", MovilizerReplyMovelet.class);
        converter = new ReplyMoveletToJsonConverter();
        expectedJsonObject = fromResource("/reports/rt-reply-movelet.json");
    }

    @Test
    public void testApply() throws Exception {
        JsonObject convertedJsonObject = converter.apply(replyMovelet);
        assertNotNull(convertedJsonObject);

        for (Map.Entry<String, JsonElement> entry : expectedJsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement actual = convertedJsonObject.get(key);
            if(actual == null) {
                fail("Value not found for key [" + key + "]");
            }
            JsonElement expected = expectedJsonObject.get(key);
            if(!actual.equals(expected)) {
                fail("Values do not match for key [" + key + "]");
            }
        }
    }
}