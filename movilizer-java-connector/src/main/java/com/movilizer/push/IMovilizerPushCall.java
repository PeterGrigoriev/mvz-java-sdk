package com.movilizer.push;

import com.google.common.base.Function;
import com.google.inject.ImplementedBy;
import com.movilitas.movilizer.v11.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v11.MovilizerMovelet;
import com.movilitas.movilizer.v11.MovilizerRequest;
import com.movilizer.connector.IMoveletKeyWithExtension;
import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.usermanagement.IMovilizerUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
@ImplementedBy(MovilizerPushCall.class)
public interface IMovilizerPushCall {
    public void doWithRequest(Function<MovilizerRequest, Void> processRequest);

    void addMovelet(MovilizerMovelet movelet, Collection<IMovilizerUser> users);


    boolean send();

    void addMovelets(Collection<MovilizerMovelet> movilizerMovelets, Collection<IMovilizerUser> users);

    void addAssignments(Iterable<IMovilizerUser> newOrResetUsers, Iterable<IMoveletKeyWithExtension> moveletKeys);

    void addUnassignments(Iterable<IMovilizerUser> users, Iterable<IMoveletKeyWithExtension> moveletKeys);

    void addMovelets(Collection<MovilizerMovelet> movilizerMovelets);

    void addMoveletDeletions(Iterable<IMoveletKeyWithExtension> moveletKeysToDelete);

    void addDocument(String pool, String key, File file) throws IOException;

    void addDocument(String pool, String key, String extension, InputStream fileInputStream) throws IOException;

    void setRequest(MovilizerRequest request);

    void addMasterdataPoolUpdate(MovilizerMasterdataPoolUpdate masterdataPoolUpdate);

    void resetRequest();

    void addMasterdataGroupReference(String masterdataKey, String pool, String group, String acknowledgementKey);

    void addMasterdataGroupDereference(String masterdataKey, String pool, String group, String acknowledgementKey);

    void addMovelets(IMoveletDataProvider moveletDataProvider, List<IMovilizerUser> users, String... templates) throws Exception;

    void addMovelets(IMoveletDataProvider moveletDataProvider, String... templates) throws Exception;
}


