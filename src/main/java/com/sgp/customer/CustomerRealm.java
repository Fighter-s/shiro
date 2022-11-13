package com.sgp.customer;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {

    // 指定加密方式
    {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 判断用户名是否为空  如果返回null的话shiro自动抛出异常
        if(StringUtils.isEmpty(userName)){
            return null;
        }

        // 根据用户名获取用户信息
        User user = getUser(userName);
        if(user == null){
            return null;
        }

        // 密码交给shiro去比对  将第一个user信息用于下边的授权
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),"customerName");
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSlat()));

        return info;
    }

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    public User getUser(String userName){
        if(userName.equals("root")){
            User user = new User();
            user.setUserName("root");
            user.setPassword("28cf327cd6b46986752f4a396aecfa23");
            user.setSlat("root");
            return user;
        }
        return null;
    }


    /**
     * 授权方法
     * @param principalCollection  认证玩之后传入的用户信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取认证完事之后的用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();

        // 获取该用户所有的角色信息
        Set<String> roles = getRoles();
        // 获取每个用户的权限信息
        Set<String> permissions = getPermissions();
        //
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    private Set<String> getRoles(){
        Set<String> roles = new HashSet<>();
        roles.add("超级管理员");
        return roles;
    }

    public Set<String> getPermissions(){
        Set<String> permission = new HashSet<>();
        permission.add("user:add");
        return permission;
    }


}
