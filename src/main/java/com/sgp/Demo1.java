package com.sgp;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class Demo1 {
    @Test
    public void test(){
        // 创建一个realm
        SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

        // 设置一个账户
        simpleAccountRealm.addAccount("root","root","超级管理员","商家");

        // 创建一个SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 将manager和realm绑定
        securityManager.setRealm(simpleAccountRealm);

        // 将manager和subject绑定
        SecurityUtils.setSecurityManager(securityManager);

        // 获取subject
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("root","root");


        // 登录
        subject.login(usernamePasswordToken);

        System.out.println(subject.isAuthenticated());

        // 授权实在认证成功之后的操作
        // simpleAccountRealm只是支持角色的授权
        System.out.println("是否拥有超级管理员角色："+subject.hasRole("超级管理员"));
        // check角色的时候如果没有这个角色就会抛出异常
        subject.checkRole("商家111");
    }
}
