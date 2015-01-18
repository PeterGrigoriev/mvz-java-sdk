package com.movilizer.util.config;

import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@gdfsuez.com
 */
public class MoveletSettings implements IMoveletSettings {

    private static final ILogger logger = ComponentLogger.getInstance(MoveletSettings.class);

    private final IMoveletCategory rootCategory;
    private final String namespace;
    private final String environment;
    private final Map<String, Integer> poolToMasterDataSystemId;
    private boolean debug;
    private final int masterdataSystemId;
    private final int version;
    private final String moveletKey;
    private final Set<IMovilizerMasterDataSystem> masterDataSystems;

    public MoveletSettings(String namespace, String environment, IMoveletCategory rootCategory, boolean debug, int masterdataSystemId, int version, String moveletKey, Set<IMovilizerMasterDataSystem> masterDataSystems) {
        this.namespace = namespace;
        this.environment = environment;
        this.rootCategory = rootCategory;
        this.debug = debug;
        this.masterdataSystemId = masterdataSystemId;
        this.version = version;
        this.moveletKey = moveletKey;
        this.masterDataSystems = masterDataSystems == null ? new HashSet<IMovilizerMasterDataSystem>() : masterDataSystems;
        this.poolToMasterDataSystemId = getPoolToMasterDataSystemIdMap(masterDataSystems);
    }

    protected static Map<String, Integer> getPoolToMasterDataSystemIdMap(Set<IMovilizerMasterDataSystem> masterDataSystems) {
        Map<String, Integer> map = newHashMap();
        if(null == masterDataSystems) {
            return map;
        }
        for (IMovilizerMasterDataSystem masterDataSystem : masterDataSystems) {
            Set<String> pools = masterDataSystem.getPools();
            for (String pool : pools) {
                if(map.containsKey(pool)) {
                    logger.error("The pool [" + pool + "] has been configured for more than one master data system. Usage in system [" + masterDataSystem.getId() + "] will be ignored.");
                    continue;
                }
                map.put(pool, masterDataSystem.getId());
            }
        }
        return map;
    }

    public MoveletSettings(String namespace, String environment, IMoveletCategory rootCategory, boolean debug, int masterdataSystemId, Set<IMovilizerMasterDataSystem> masterDataSystems) {
        this(namespace, environment, rootCategory, debug, masterdataSystemId, 0, "", masterDataSystems);
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getEnvironment() {
        return environment;
    }

    public IMoveletCategory getRootCategory() {
        return rootCategory;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public int getMasterDataSystemId() {
        return masterdataSystemId;
    }

    @Override
    public Set<IMovilizerMasterDataSystem> getMasterDataSystems() {
        return masterDataSystems;
    }

    @Override
    public Integer getMasterDataSystemId(String pool) {
        Integer systemId = poolToMasterDataSystemId.get(pool);
        if(systemId != null) {
            return systemId;
        }

        if(masterdataSystemId != 0) {
            return masterdataSystemId;
        }

        return null;
    }

    @Override
    public int getMoveletVersion() {
        return version;
    }

    @Override
    public String getMoveletKey() {
        return moveletKey;
    }
}
