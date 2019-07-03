package cn.it.yip.web.admin;

import cn.it.yip.aspect.WebLog;
import cn.it.yip.pojo.Type;
import cn.it.yip.pojo.User;
import cn.it.yip.service.TypeServcie;
import cn.it.yip.service.UserService;
import cn.it.yip.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 01:36
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;


    @GetMapping
    public String admin() {
        return "admin/login";
    }

    @GetMapping("/editPassword")
    public String password(){
        return "admin/changePassword";
    }

    @WebLog(description = "登录用户接口")
    @PostMapping("/login")
    public String login(User user, HttpSession session,
                        RedirectAttributes attributes) {
        User checkUser = userService.checkUser(user);
        if (checkUser != null) {
            session.setAttribute("user", checkUser);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @WebLog(description = "修改密码接口")
    @PostMapping("/changePassword")
    public String changePassword(UserVo userVo, RedirectAttributes attributes){
        if (StringUtils.equals(userVo.getPassword(),userVo.getNewPassword())) {
            attributes.addFlashAttribute("message","旧密码与新密码不能相同哦！！");
            return "redirect:editPassword";
        }
        User user = userService.changeUser(userVo);
        if (user != null) {
            return "redirect:/admin";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:editPassword";
        }
    }


    @WebLog(description = "用户访问后台首页接口")
    @GetMapping("blogs")
    public String blogs() {
        return "admin/blogs";
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @WebLog(description = "退出登录接口")
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
