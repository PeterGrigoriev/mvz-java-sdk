package com.movilizer.usermanagement;

import com.google.common.base.Predicate;

import static org.apache.commons.lang.ArrayUtils.contains;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class IsUserStatus implements Predicate<IMovilizerUser> {
    private final MovilizerUserStatus[] statuses;

    public IsUserStatus(MovilizerUserStatus... statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean apply(IMovilizerUser user) {
        return contains(statuses, user.getStatus());
    }
}
