package com.movilizer.container;

import com.movilitas.movilizer.v14.MovilizerGenericDataContainer;
import com.movilitas.movilizer.v14.MovilizerGenericDataContainerEntry;
import com.movilitas.movilizer.v14.MovilizerGenericUploadDataContainer;
import com.movilizer.util.datetime.DateTimeUtils;
import com.movilizer.util.json.JsonUtils;
import org.testng.annotations.Test;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.movilizer.util.datetime.DateTimeUtils.asXmlGregorianCalendar;
import static com.movilizer.util.datetime.Dates.now;
import static org.testng.Assert.assertEquals;

public class MovilizerJsonContainerTest {

    public static final XMLGregorianCalendar TIMESTAMP = asXmlGregorianCalendar(now());
    public static final String DEVICE_ADDRESS = "@a.b.com";
    public static final String CONTAINER_KEY = "someKey";
    public static final String MOVELET_KEY = "TestMovelet";
    public static final String KEY_EXTENSION = "TestExtension";

    @Test
    public void testFromUploadDataContainer() throws Exception {
        MovilizerGenericUploadDataContainer dataContainer = createContainer();
        IMovilizerJsonContainer container = MovilizerJsonContainer.fromUploadDataContainer(dataContainer);

        assertEquals(container.getCreationDate(), DateTimeUtils.asDate(TIMESTAMP));
        assertEquals(container.getDeviceAddress(), DEVICE_ADDRESS);
        assertEquals(container.getMoveletKey(), MOVELET_KEY);
        assertEquals(container.getKey(), CONTAINER_KEY);
        assertEquals(container.getData(), JsonUtils.toJsonObject(JSON));

    }

    private final static String JSON = "{\"fieldOne\":12, \"fieldTwo\":\"ABC\"}";

    private MovilizerGenericUploadDataContainer createContainer() {
        MovilizerGenericUploadDataContainer container = new MovilizerGenericUploadDataContainer();
        container.setDeviceAddress(DEVICE_ADDRESS);
        container.setKey(CONTAINER_KEY);
        container.setCreationTimestamp(TIMESTAMP);
        container.setMoveletKey(MOVELET_KEY);
        container.setMoveletKeyExtension(KEY_EXTENSION);
        container.setData(new MovilizerGenericDataContainer());
        MovilizerGenericDataContainerEntry entry = new MovilizerGenericDataContainerEntry();
        entry.setName("jsonString");
        entry.setValstr(JSON);
        container.getData().getEntry().add(entry);
        return container;
    }
}