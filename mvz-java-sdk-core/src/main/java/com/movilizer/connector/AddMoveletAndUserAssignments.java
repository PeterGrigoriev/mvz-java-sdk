package com.movilizer.connector;

import com.google.common.base.Function;
import com.movilitas.movilizer.v15.MovilizerMovelet;
import com.movilitas.movilizer.v15.MovilizerMoveletSet;
import com.movilitas.movilizer.v15.MovilizerRequest;
import com.movilizer.usermanagement.IMovilizerUser;
import com.movilizer.usermanagement.MovilizerUser;

import java.util.Collection;
import java.util.List;

import static com.movilizer.connector.MoveletDelete.getMoveletDelete;
import static com.movilizer.connector.MoveletKeyWithExtension.keyWithExtension;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class AddMoveletAndUserAssignments implements Function<MovilizerRequest, Void> {
    private final MovilizerMovelet movelet;
    private final IMovilizerUser[] users;

    public AddMoveletAndUserAssignments(MovilizerMovelet movelet, IMovilizerUser... users) {
        this.movelet = movelet;
        this.users = users;
    }

    public AddMoveletAndUserAssignments(MovilizerMovelet movelet, Collection<IMovilizerUser> users) {
        this(movelet, users.toArray(new IMovilizerUser[users.size()]));
    }

    @Override
    public Void apply(MovilizerRequest request) {
        List<MovilizerMoveletSet> moveletSets = request.getMoveletSet();
        MovilizerMoveletSet moveletSet = new MovilizerMoveletSet();
        moveletSets.add(moveletSet);
        moveletSet.getMovelet().add(movelet);
        for (IMovilizerUser user : users) {
            moveletSet.getParticipant().add(MovilizerUser.toParticipant(user));
        }

        if(movelet.getMoveletVersion() == null) {
            request.getMoveletDelete().add(getMoveletDelete(keyWithExtension(movelet)));
        }
        return null;
    }

}
