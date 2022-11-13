package com.sgp.customer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class customerRealmDemo {

    @Test
    public void test(){
        CustomerRealm realm = new CustomerRealm();

        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(realm);

        SecurityUtils.setSecurityManager(manager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("root","root");

        subject.login(token);

        System.out.println(subject.isAuthenticated());

        System.out.println(new Md5Hash("root","root",1024).toString());


        System.out.println(subject.hasRole("超级管理员"));
        System.out.println(subject.isPermittedAll("user:add"));
    }
}
