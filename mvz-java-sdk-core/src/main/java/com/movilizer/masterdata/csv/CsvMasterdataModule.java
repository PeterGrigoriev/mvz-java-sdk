package com.movilizer.masterdata.csv;

import com.google.common.base.Predicate;
import com.google.gson.JsonObject;
import com.movilizer.masterdata.IMasterdataAcknowledger;
import com.movilizer.masterdata.IMasterdataSource;
import com.movilizer.masterdata.operation.AddGroup;
import com.movilizer.module.MovilizerModule;

import java.io.File;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class CsvMasterdataModule extends MovilizerModule {
    private final File file;
    private String defaultGroup;
    private Predicate<JsonObject> filter;

    public static CsvMasterdataModule forFile(File file) {
        return new CsvMasterdataModule(file);
    }

    private CsvMasterdataModule(File file) {
        this.file = file;
    }

    public CsvMasterdataModule setFilter(Predicate<JsonObject> filter) {
        this.filter = filter;
        return this;
    }

    public CsvMasterdataModule setDefaultGroup(String defaultGroup) {
        this.defaultGroup = defaultGroup;
        return this;
    }

    @Override
    protected void setUp() {
        CsvMasterDataSource source = new CsvMasterDataSource(file);
        if (defaultGroup != null) {
            source.setPostProcessPoolUpdate(new AddGroup(defaultGroup));
        }
        if(filter != null) {
            source.setFilter(filter);
        }

        bind(IMasterdataSource.class).toInstance(source);
        bind(IMasterdataAcknowledger.class).toInstance(source);
    }
}
