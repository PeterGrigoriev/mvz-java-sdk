package com.movilizer.usermanagement;

import java.util.HashMap;
import java.util.Map;

import static com.movilizer.util.string.StringUtils.isNullOrEmpty;
import static com.movilizer.util.string.StringUtils.joinOmitEmpties;
import static java.lang.String.format;


/**
 * @author Peter.Grigoriev@movilizer.com
 * @author philippe.guillamet@altran.com
 */
public class MovilizerUserUtils {

    public static Map<Integer, IMovilizerUser> employeeNumberToUserMap(Iterable<IMovilizerUser> users) {
        Map<Integer, IMovilizerUser> map = new HashMap<Integer, IMovilizerUser>();
        for (IMovilizerUser user : users) {
            int employeeNumber = user.getEmployeeNumber();
            map.put(employeeNumber, user);

        }
        return map;
    }

    public static String userToLongString(IMovilizerUser user) {
        return joinOmitEmpties(", "
                , "name is " + user.getName()
                , "employee number is " + user.getEmployeeNumber()
                , isNullOrEmpty(user.getEmail()) ? null : "email is " + user.getEmail()
                , isNullOrEmpty(user.getPhone()) ? null : "phone is " + user.getPhone()
                , "invitation method is " + user.getInvitationMethod()
                , "status is " + user.getStatus());
    }

    private static String getDeviceAddressSave(IMovilizerUser user) {
        try {
            return user.getDeviceAddress();
        } catch (Throwable throwable) {
            return "ERR";
        }
    }

    public static String userToShortString(IMovilizerUser user) {
        return format("[%s|%s|%s]", user.getName(), user.getEmployeeNumber(), getDeviceAddressSave(user));
    }

    public static String usersToShortString(Iterable<IMovilizerUser> users, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (IMovilizerUser user : users) {
            if (!isFirst) {
                stringBuilder.append(separator);
            } else {
                isFirst = false;
            }
            stringBuilder.append(userToShortString(user));
        }
        return stringBuilder.toString();
    }
}
