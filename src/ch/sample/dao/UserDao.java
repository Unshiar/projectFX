package ch.sample.dao;

import ch.sample.model.UserModel;

import java.util.List;

public interface UserDao {
    UserModel findById(int id);
    void save(UserModel user);
    void delete(UserModel user);
    void update(UserModel user);
    List<UserModel> findAll();
}
