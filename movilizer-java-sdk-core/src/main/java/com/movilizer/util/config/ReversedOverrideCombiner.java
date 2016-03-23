package com.movilizer.util.config;

import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.OverrideCombiner;

/**
 * @author Peter.Grigoriev@movilizer.com.
 */
public class ReversedOverrideCombiner extends OverrideCombiner {
    @Override
    public ConfigurationNode combine(ConfigurationNode node1, ConfigurationNode node2) {
        return super.combine(node2, node1);
    }
}
