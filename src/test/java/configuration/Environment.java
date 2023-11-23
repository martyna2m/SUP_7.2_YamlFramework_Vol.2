package configuration;

import lombok.Getter;

@Getter
public class Environment {
    private String envName;
    private String appUrl;
    private String webTitle;
    private String login;
    private String password;
    private String exchangeMailbox;
    private String mailboxPassword;
    private int browserImplicitTimeOut;
    private boolean active;

}
