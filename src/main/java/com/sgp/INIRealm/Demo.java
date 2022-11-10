package com.sgp.INIRealm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class Demo {
    @Test
    public void test(){
        // 创建inirealm
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");

        // 创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        // manager和realm绑定
        defaultSecurityManager.setRealm(iniRealm);

        //
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        //
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("root", "root");
        subject.login(usernamePasswordToken);

        System.out.println(subject.isAuthenticated());

        // 授权
        subject.checkPermission("user:select");


    }
}
