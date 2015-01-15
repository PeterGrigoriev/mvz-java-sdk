package com.movilizer.util.config;

import com.movilizer.masterdata.IMasterdataFieldNames;
import com.movilizer.masterdata.IMasterdataXmlSetting;
import com.movilizer.masterdata.MasterdataFieldNames;
import com.movilizer.util.collection.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.movilizer.util.collection.CollectionUtils.toStringSet;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class MovilizerMasterDataSystem implements IMovilizerMasterDataSystem {
    private final String name;
    private final int id;
    private final Set<String> pools;


    public MovilizerMasterDataSystem(String name, int id, Set<String> pools) {
        this.name = name;
        this.id = id;
        this.pools = pools;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Set<String> getPools() {
        return pools;
    }

    public static IMovilizerMasterDataSystem fromXml(String xml) throws ConfigurationException {
        XMLConfiguration xmlConfiguration = new XMLConfiguration();
        xmlConfiguration.load(new StringReader(xml));
        return read(xmlConfiguration);
    }

    public static IMovilizerMasterDataSystem read(HierarchicalConfiguration configuration) {
        int id = configuration.getInt("id");
        String name = configuration.getString("name");

        List poolList = configuration.configurationAt("pools").getList("pool");
        Set<String> pools = toStringSet(poolList);

        return new MovilizerMasterDataSystem(name, id, pools);
    }
}
