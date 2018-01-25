package bhz.mapper;

import java.util.List;

import bhz.entity.User;


public interface UserMapper {

    User selectByPrimaryKey(String id);

    List<User> findAll() ;
}