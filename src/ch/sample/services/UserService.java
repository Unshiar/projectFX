package ch.sample.services;

import ch.sample.dao.UserDaoImpl;
import ch.sample.model.UserModel;

import java.util.List;

public class UserService {
    private UserDaoImpl userDaoImpl = new UserDaoImpl();

    public UserService() {

    }

    public UserModel findUser(int id) {
        return userDaoImpl.findById(id);
    }

    public void saveUser(UserModel user) {
        userDaoImpl.save(user);
    }

    public void deleteUser(UserModel user) {
        userDaoImpl.delete(user);
    }

    public void updateUser(UserModel user) {
        userDaoImpl.update(user);
    }

    public List<UserModel> findAllUsers() {
        return userDaoImpl.findAll();
    }
}
