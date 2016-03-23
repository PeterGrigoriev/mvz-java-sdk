package com.movilizer.util.config;

import org.testng.annotations.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.testng.Assert.assertEquals;

public class MovilizerMasterDataSystemTest {

    private static final String XML = "<master-data-system>\n" +
            "    <id>23456</id>\n" +
            "    <name>First Master Data System</name>\n" +
            "    <pools>\n" +
            "        <pool>poolOne</pool>\n" +
            "        <pool>poolTwo</pool>\n" +
            "    </pools>\n" +
            "</master-data-system>\n";

    @Test
    public void testFromXml() throws Exception {
        IMovilizerMasterDataSystem masterDataSystem = MovilizerMasterDataSystem.fromXml(XML);
        assertEquals(masterDataSystem.getId(), 23456);
        assertEquals(masterDataSystem.getName(), "First Master Data System");
        assertEquals(masterDataSystem.getPools(), newHashSet("poolOne", "poolTwo"));
    }
}