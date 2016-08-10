package com.movilizer.moveletbuilder;

import com.movilitas.movilizer.v15.MovilizerMovelet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class EmptyMoveletBuilder implements IMoveletBuilder {
    private static final List<MovilizerMovelet> NO_MOVELETS = new ArrayList<MovilizerMovelet>();

    @Override
    public List<MovilizerMovelet> buildMovelets() {
        return NO_MOVELETS;
    }

    public static final IMoveletBuilder instance = new EmptyMoveletBuilder();

    public static IMoveletBuilder getInstance() {
        return instance;
    }

    @Override
    public void onRequestAction(RequestEvent action) {

    }
}
