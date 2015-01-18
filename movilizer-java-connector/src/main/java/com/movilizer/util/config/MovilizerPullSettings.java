package com.movilizer.util.config;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MovilizerPullSettings implements IMovilizerPullSettings {
    private final int numberOfReplies;

    public MovilizerPullSettings(int numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public int getNumberOfReplies() {
        return numberOfReplies;
    }
}
