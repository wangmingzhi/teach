package com.teachpmp.server.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "system")
public class SystemConfig {
    private PasswordKeyConfig pwdKey;
    private List<String> securityIgnoreUrls;
    private WxConfig wx;
    private QnConfig qn;

    public PasswordKeyConfig getPwdKey() {
        return pwdKey;
    }

    public void setPwdKey(PasswordKeyConfig pwdKey) {
        this.pwdKey = pwdKey;
    }

    public List<String> getSecurityIgnoreUrls() {
        return securityIgnoreUrls;
    }

    public void setSecurityIgnoreUrls(List<String> securityIgnoreUrls) {
        this.securityIgnoreUrls = securityIgnoreUrls;
    }

    public WxConfig getWx() {
        return wx;
    }

    public void setWx(WxConfig wx) {
        this.wx = wx;
    }

    public QnConfig getQn() {
        return qn;
    }

    public void setQn(QnConfig qn) {
        this.qn = qn;
    }
}
