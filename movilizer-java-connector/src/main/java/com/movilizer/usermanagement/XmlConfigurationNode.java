package com.movilizer.usermanagement;

import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.DefaultConfigurationNode;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class XmlConfigurationNode extends DefaultConfigurationNode {
    public static XmlConfigurationNode newNode(String name) {
        XmlConfigurationNode node = new XmlConfigurationNode();
        node.setName(name);
        return node;
    }

    public XmlConfigurationNode addProperty(String name, Object value) {
        if (value == null) {
            return this;
        }
        ConfigurationNode childNode = new DefaultConfigurationNode();
        childNode.setName(name);
        childNode.setValue(value);
        addChild(childNode);
        return this;
    }

    public static Object getConfigurationProperty(ConfigurationNode node, String name) {
        //noinspection unchecked
        List<ConfigurationNode> children = node.getChildren(name);

        if (children == null || children.isEmpty()) {
            return null;
        }
        return children.get(0).getValue();
    }
}
