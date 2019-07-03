package cn.it.yip.repository;

import cn.it.yip.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 01:34
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
