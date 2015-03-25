package com.movilizer.moveletbuilder;

import com.movilitas.movilizer.v12.MovilizerMovelet;

import java.util.List;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public interface IMoveletBuilder extends IMoveletPushListener {
    List<MovilizerMovelet> buildMovelets();
}
