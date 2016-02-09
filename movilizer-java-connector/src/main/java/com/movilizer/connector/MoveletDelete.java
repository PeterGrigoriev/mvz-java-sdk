package com.movilizer.connector;

import com.google.common.base.Function;
import com.movilitas.movilizer.v14.MovilizerMoveletDelete;
import com.movilitas.movilizer.v14.MovilizerRequest;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletDelete implements Function<MovilizerRequest, Void> {

    private final Iterable<IMoveletKeyWithExtension> moveletKeysToDelete;

    public MoveletDelete(Iterable<IMoveletKeyWithExtension> moveletKeysToDelete) {
        this.moveletKeysToDelete = moveletKeysToDelete;
    }

    public MoveletDelete(IMoveletKeyWithExtension... moveletKeysToDelete) {
        this(asList(moveletKeysToDelete));
    }


    public static MovilizerMoveletDelete getMoveletDelete(IMoveletKeyWithExtension moveletToDeleteKey) {
        MovilizerMoveletDelete deleteOldOne = new MovilizerMoveletDelete();

        deleteOldOne.setMoveletKey(moveletToDeleteKey.getMoveletKey());
        deleteOldOne.setMoveletKeyExtension(moveletToDeleteKey.getMoveletExtension());

        return deleteOldOne;
    }

    @Override
    public Void apply(MovilizerRequest request) {
        List<MovilizerMoveletDelete> moveletDelete = request.getMoveletDelete();
        for (IMoveletKeyWithExtension keyWithExtension : moveletKeysToDelete) {
            moveletDelete.add(getMoveletDelete(keyWithExtension));
        }
        return null;
    }
}
