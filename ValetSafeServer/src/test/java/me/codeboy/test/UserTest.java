package me.codeboy.test;

import me.codeboy.action.UserAction;
import me.codeboy.bean.User;
import me.codeboy.common.framework.hibernate.core.CBHibernateTask;
import me.codeboy.common.framework.workflow.core.CBResponseController;
import org.hibernate.Session;


/**
 * user test
 * Created by yuedong.li on 6/8/16.
 */
public class UserTest {
    public static void main(String[] args) {
//        UserAction action = new UserAction();
//        CBResponseController.setDebug(true);
//        action.setName("1234");
//        action.addUser();

        new CBHibernateTask<Void>(){
            @Override
            public Void doTask(Session session) {
                String sql = "from User where id=" + 1;
                int size = session.createQuery(sql).list().size();
                User user = (User) session.get(User.class, 1);
                user.getName();
                user.getId();
                return null;
            }
        }.execute();
    }
}
