package com.movilizer.moveletbuilder;

import com.movilitas.movilizer.v15.MovilizerMovelet;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMoveletBuilder extends IMoveletPushListener {
    List<MovilizerMovelet> buildMovelets();
}
