package dao;

import bean.User;

public class UserDAO extends AbstractDAO<User, Integer>{

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
