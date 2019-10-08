package com.example.demo.shiro;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.example.demo.bean.SysUser;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/*自定义的Realm类继承AuthorizingRealm类，并且重载doGetAuthorizationInfo和doGetAuthenticationInfo两个方法。
 doGetAuthorizationInfo： 权限认证，即登录过后，每个身份不一定，对应的所能看的页面也不一样。
 doGetAuthenticationInfo：身份认证。即登录通过账号和密码验证登陆人的身份信息。*/
public class CustomRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 该方法需要实现身份认证，即用户名密码的校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        SysUser user = userService.getUserByName(userName);
        if(user==null){
            throw new AccountException("用户名不正确");
        }else if(!password.equals(user.getPassword())){
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, password,getName());
    }
}
