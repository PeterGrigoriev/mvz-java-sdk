package com.movilizer.pull;


import com.google.inject.ImplementedBy;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
@ImplementedBy(MovilizerPullRunner.class)
public interface IMovilizerPullRunner {
    boolean run();
}
