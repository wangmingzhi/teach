package com.teachpmp.server.service;

import com.teachpmp.server.configuration.property.SystemConfig;
import com.teachpmp.server.entity.User;
import com.teachpmp.server.utils.CommonUtils;
import com.teachpmp.server.utils.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    @Autowired
    private UserService userService;
    @Autowired
    private SystemConfig systemConfig;


    /**
     * @param account username
     * @param password password
     * @return boolean
     */
    public boolean authUser(String account, String password) {
        User user = userService.getUserByAccount(account);
        return authUser(user, account, password);
    }


    public boolean authUser(User user, String username, String password) {
        if (user == null) {
            return false;
        }
        String encodePwd = user.getPassword();
        if (null == encodePwd || encodePwd.length() == 0) {
            return false;
        }
        return encodePwd.equals(pwdEncode(password));
    }

    public String pwdEncode(String password) {
//        String rsaStr = RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), password);
        return CommonUtils.md5(password);
    }

    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
    }


}
