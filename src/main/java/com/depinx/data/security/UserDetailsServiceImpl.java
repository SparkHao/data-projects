package com.depinx.data.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String wallet) throws UsernameNotFoundException
    {
        LoginUser loginUser = new LoginUser();
        loginUser.setWallet(wallet);
        return createLoginUser(loginUser);
    }

    public UserDetails createLoginUser(LoginUser loginUser)
    {
        return loginUser;
    }
}
