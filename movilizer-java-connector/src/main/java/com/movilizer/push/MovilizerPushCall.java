package com.movilizer.push;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.movilitas.movilizer.v12.*;
import com.movilizer.connector.*;
import com.movilizer.document.IMovilizerDocumentUploader;
import com.movilizer.moveletbuilder.IMoveletDataProvider;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.util.template.ITemplateRepository;
import com.movilizer.util.template.TemplateMoveletBuilder;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import static com.movilizer.usermanagement.MovilizerUser.toParticipant;

/**
 * @author Peter.Grigoriev@movilitas.com
 */
public class MovilizerPushCall extends MovilizerCall implements IMovilizerPushCall {

    private final IMovilizerDocumentUploader documentUploader;
    private final ITemplateRepository templateRepository;

    @Inject
    public MovilizerPushCall(IMovilizerCloudSystem system,
                             @Nullable IProxyInfo proxyInfo,
                             IMovilizerRequestSender requestSender,
                             @Nullable IMovilizerDocumentUploader documentUploader,
                             @Nullable ITemplateRepository templateRepository) {
        super(system, proxyInfo, requestSender);
        this.documentUploader = documentUploader;
        this.templateRepository = templateRepository;
    }

    @Override
    protected int getNumberOfRepliesToReceive() {
        return 0;
    }

    @Override
    public void doWithRequest(Function<MovilizerRequest, Void> processRequest) {
        processRequest.apply(accessRequest());
    }

    public void addMovelet(MovilizerMovelet movelet, Collection<IMovilizerUser> users) {
        doWithRequest(new AddMoveletAndUserAssignments(movelet, users));
    }

    @Override
    public boolean send() {
        return doCallMovilizerCloud().isSuccess();
    }

    @Override
    public void addMovelets(Collection<MovilizerMovelet> movelets, Collection<IMovilizerUser> users) {
        for (MovilizerMovelet movelet : movelets) {
            addMovelet(movelet, users);
        }
    }

    @Override
    public void addAssignments(Iterable<IMovilizerUser> users, Iterable<IMoveletKeyWithExtension> moveletKeys) {
        List<MovilizerMoveletAssignment> assignments = accessRequest().getMoveletAssignment();
        for (IMoveletKeyWithExtension moveletKey : moveletKeys) {
            MovilizerMoveletAssignment assignment = toAssignment(moveletKey, users);
            assignments.add(assignment);
        }
    }

    @Override
    public void addUnassignments(Iterable<IMovilizerUser> users, Iterable<IMoveletKeyWithExtension> moveletKeys) {
        List<MovilizerMoveletAssignmentDelete> assignmentDeletes = accessRequest().getMoveletAssignmentDelete();
        for (IMoveletKeyWithExtension moveletKey : moveletKeys) {
            for (IMovilizerUser user : users) {
                assignmentDeletes.add(getAssignmentDelete(moveletKey, user));
            }
        }
    }

    public static MovilizerMoveletAssignmentDelete getAssignmentDelete(IMoveletKeyWithExtension moveletKey, IMovilizerUser user) {
        MovilizerMoveletAssignmentDelete assignmentDelete = new MovilizerMoveletAssignmentDelete();
        assignmentDelete.setMoveletKey(moveletKey.getMoveletKey());
        assignmentDelete.setMoveletKeyExtension(moveletKey.getMoveletExtension());
        assignmentDelete.setDeviceAddress(user.getDeviceAddress());
        return assignmentDelete;
    }

    @Override
    public void addMovelets(Collection<MovilizerMovelet> movilizerMovelets) {
        addMovelets(movilizerMovelets, new ArrayList<IMovilizerUser>());
    }

    @Override
    public void addMoveletDeletions(Iterable<IMoveletKeyWithExtension> moveletKeysToDelete) {
        doWithRequest(new MoveletDelete(moveletKeysToDelete));
    }

    @Override
    public void addDocument(String pool, String key, File file) throws IOException {
        assert documentUploader != null;
        documentUploader.addDocument(accessRequest(), pool, key, file);
    }

    @Override
    public void addDocument(String pool, String key, String extension, InputStream fileInputStream) throws IOException {
        assert documentUploader != null;
        documentUploader.addDocument(accessRequest(), pool, key, extension, fileInputStream);
    }

    @Override
    public void addMasterdataPoolUpdate(MovilizerMasterdataPoolUpdate masterdataPoolUpdate) {
        accessRequest().getMasterdataPoolUpdate().add(masterdataPoolUpdate);
    }

    @Override
    public void addMasterdataGroupReference(String masterdataKey, String pool, String group, String acknowledgementKey) {
        MovilizerMasterdataPoolUpdate update = new MovilizerMasterdataPoolUpdate();
        update.setPool(pool);
        MovilizerMasterdataReference reference = new MovilizerMasterdataReference();
        reference.setGroup(group);
        reference.setKey(masterdataKey);
        reference.setMasterdataAckKey(acknowledgementKey);
        update.getReference().add(reference);

        addMasterdataPoolUpdate(update);
    }

    @Override
    public void addMasterdataGroupDereference(String masterdataKey, String pool, String group, String acknowledgementKey) {
        MovilizerMasterdataPoolUpdate update = new MovilizerMasterdataPoolUpdate();

        update.setPool(pool);
        MovilizerMasterdataDelete delete = new MovilizerMasterdataDelete();
        delete.setGroup(group);
        delete.setKey(masterdataKey);
        delete.setMasterdataAckKey(acknowledgementKey);
        update.getDelete().add(delete);

        addMasterdataPoolUpdate(update);
    }


    @Override
    public void addMovelets(IMoveletDataProvider moveletDataProvider, List<IMovilizerUser> users, String... templates) throws Exception {
        TemplateMoveletBuilder templateMoveletBuilder = new TemplateMoveletBuilder(moveletDataProvider, templateRepository, templates);
        addMovelets(templateMoveletBuilder.buildMovelets(), users);
    }

    @Override
    public void addMovelets(IMoveletDataProvider moveletDataProvider, String... templates) throws Exception {
        List<IMovilizerUser> users = new ArrayList<IMovilizerUser>();
        addMovelets(moveletDataProvider, users, templates);
    }

    @Override
    public void setRequest(MovilizerRequest request) {
        resetRequest(request);
    }


    public static MovilizerMoveletAssignment toAssignment(IMoveletKeyWithExtension moveletKey, Iterable<IMovilizerUser> users) {
        MovilizerMoveletAssignment assignment = new MovilizerMoveletAssignment();
        assignment.setMoveletKey(moveletKey.getMoveletKey());
        assignment.setMoveletKeyExtension(moveletKey.getMoveletExtension());
        List<MovilizerParticipant> participants = assignment.getParticipant();

        for (IMovilizerUser user : users) {
            participants.add(toParticipant(user));
        }

        return assignment;
    }



}
