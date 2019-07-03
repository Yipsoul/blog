package cn.it.yip.service;

import cn.it.yip.pojo.User;
import cn.it.yip.repository.UserRepository;
import cn.it.yip.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 01:33
 **/
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User checkUser(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), DigestUtils.md5Hex(user.getPassword()));
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("root"));
    }
    @Transactional
    public User changeUser(UserVo userVo) {
        String password =DigestUtils.md5Hex(userVo.getPassword());
        User user = this.userRepository.findByUsernameAndPassword(userVo.getUsername(), password);
        if(user != null){
            user.setPassword(DigestUtils.md5Hex(userVo.getNewPassword()));
            this.userRepository.saveAndFlush(user);
            return user;
        }
        return user;
    }
}
