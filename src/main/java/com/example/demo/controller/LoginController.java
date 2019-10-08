package com.example.demo.controller;

import com.example.demo.bean.SysUser;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> map) {
        System.out.println(map.get("name")+"-----"+map.get("value"));
        return "200";
    }

    @PostMapping("/login2")
    public Map<String,String> login(SysUser user) {
        Map<String,String> map = new HashMap<>();
        //创建一个subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
        }catch (UnknownAccountException uae){
            map.put("status","500");
            map.put("msg","用户名不存在");
            return  map;
        }catch (IncorrectCredentialsException ice) {
            map.put("status","500");
            map.put("msg","密码不正确");
            return  map;
        } catch (LockedAccountException lae) {
            map.put("status","500");
            map.put("msg","账户已锁定");
            return  map;
        } catch (ExcessiveAttemptsException eae) {
            map.put("status","500");
            map.put("msg","用户名或密码错误次数过多");
            return  map;
        } catch (AuthenticationException ae) {
            map.put("status","500");
            map.put("msg","用户名或密码不正确");
            return  map;
        }
        if (subject.isAuthenticated()) {
            map.put("status","200");
            map.put("msg","登录成功");
            return  map;
        } else {
            token.clear();
            map.put("status","500");
            map.put("msg","登录失败");
            return map;
        }
    }

    @PostMapping("/getName")
    public Map<String,String> getName(){
        Map<String,String> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String userName = (String) subject.getPrincipal();
        map.put("id",userName);
        return map;
    }
}