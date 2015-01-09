package com.movilizer.connector;

import com.movilitas.movilizer.v11.MovilizerMovelet;
import com.movilitas.movilizer.v11.MovilizerMoveletDelete;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MoveletKeyWithExtension implements IMoveletKeyWithExtension {
    private final String moveletKey;
    private final String moveletExtension;

    private MoveletKeyWithExtension(String moveletKey, String moveletExtension) {
        this.moveletKey = moveletKey;
        this.moveletExtension = moveletExtension;
    }

    @Override
    public String getMoveletKey() {
        return moveletKey;
    }

    @Override
    public String getMoveletExtension() {
        return moveletExtension;
    }

    public static IMoveletKeyWithExtension keyWithExtension(String key, String extension) {
        return new MoveletKeyWithExtension(key, extension);
    }

    public static List<IMoveletKeyWithExtension> keysWithExtension(String moveletExtension, String... keys) {
        List<IMoveletKeyWithExtension> result = new ArrayList<IMoveletKeyWithExtension>();
        for (String key : keys) {
            result.add(keyWithExtension(key, moveletExtension));
        }
        return result;
    }

    public static IMoveletKeyWithExtension keyWithExtension(MovilizerMovelet movelet) {
        return keyWithExtension(movelet.getMoveletKey(), movelet.getMoveletKeyExtension());
    }

    public static IMoveletKeyWithExtension keyWithExtension(MovilizerMoveletDelete moveletDelete) {
        return keyWithExtension(moveletDelete.getMoveletKey(), moveletDelete.getMoveletKeyExtension());
    }

    @Override
    public String toString() {
        return String.format("<moveletKey='%s', moveletExtension='%s'>", moveletKey, moveletExtension);
    }
}
