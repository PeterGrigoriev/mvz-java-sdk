package com.movilizer.util.config;

import java.util.Set;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public interface IMovilizerMasterDataSystem {
    String getName();
    int getId();
    Set<String> getPools();
}
