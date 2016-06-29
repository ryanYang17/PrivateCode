package me.codeboy.test;

import me.codeboy.action.UserAction;
import me.codeboy.common.framework.workflow.core.CBResponseController;

/**
 * user test
 * Created by yuedong.li on 6/8/16.
 */
public class UserTest {
    public static void main(String[] args) {
        UserAction action = new UserAction();
        CBResponseController.setDebug(true);
        action.setName("1234");
        action.addUser();
    }
}
