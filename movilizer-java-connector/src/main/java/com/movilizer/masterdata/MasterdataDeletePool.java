package com.movilizer.masterdata;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.movilitas.movilizer.v11.MovilizerMasterdataDelete;
import com.movilitas.movilizer.v11.MovilizerMasterdataPoolUpdate;
import com.movilitas.movilizer.v11.MovilizerRequest;
import com.movilizer.push.IMovilizerPushCall;
import com.movilizer.util.logger.ComponentLogger;
import com.movilizer.util.logger.ILogger;

import static java.lang.String.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@Singleton
public class MasterdataDeletePool implements IMasterdataDeletePool {
    final IMovilizerPushCall pushCall;


    private final ILogger logger = ComponentLogger.getInstance("Masterdata");

    @Inject
    public MasterdataDeletePool(IMovilizerPushCall pushCall) {
        this.pushCall = pushCall;
    }


    @Override
    public void delete(final String pool) {
        pushCall.doWithRequest(new Function<MovilizerRequest, Void>() {
            @Override
            public Void apply(MovilizerRequest request) {
                logger.info(format("Deleting all entries from the pool: '%s'", pool));
                MovilizerMasterdataPoolUpdate poolUpdate = new MovilizerMasterdataPoolUpdate();
                poolUpdate.setPool(pool);
                request.getMasterdataPoolUpdate().add(poolUpdate);
                MovilizerMasterdataDelete masterdataDelete = new MovilizerMasterdataDelete();
                poolUpdate.getDelete().add(masterdataDelete);
                return null;
            }
        });

        pushCall.send();
    }
}
