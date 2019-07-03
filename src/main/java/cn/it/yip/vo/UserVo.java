package cn.it.yip.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-27 17:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String username;
    private String password;
    private String newPassword;
}
