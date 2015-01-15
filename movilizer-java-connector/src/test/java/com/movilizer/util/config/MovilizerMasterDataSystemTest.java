package com.movilizer.util.config;

import junit.framework.TestCase;

import static com.google.common.collect.Sets.newHashSet;

public class MovilizerMasterDataSystemTest extends TestCase {

    public static final String XML = "<master-data-system>\n" +
            "    <id>23456</id>\n" +
            "    <name>First Master Data System</name>\n" +
            "    <pools>\n" +
            "        <pool>poolOne</pool>\n" +
            "        <pool>poolTwo</pool>\n" +
            "    </pools>\n" +
            "</master-data-system>\n";

    public void testFromXml() throws Exception {
        IMovilizerMasterDataSystem masterDataSystem = MovilizerMasterDataSystem.fromXml(XML);
        assertEquals(masterDataSystem.getId(), 23456);
        assertEquals(masterDataSystem.getName(), "First Master Data System");
        assertEquals(masterDataSystem.getPools(), newHashSet("poolOne", "poolTwo"));
    }
}