package com.github.adrian83.mordeczki.mailer.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // TODO ??
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

	private String host;
    private int port;
	private String username;
	private String password;
	private String transportProtocol;
    private boolean smtpAuth;
    private boolean smtpStarttls;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public boolean isSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(boolean smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public boolean isSmtpStarttls() {
        return smtpStarttls;
    }

    public void setSmtpStarttls(boolean smtpStarttls) {
        this.smtpStarttls = smtpStarttls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MailProperties{");
        sb.append("host=").append(host);
        sb.append(", port=").append(port);
        sb.append(", username=").append(username);
        sb.append(", password=").append("****");
        sb.append(", transportProtocol=").append(transportProtocol);
        sb.append(", smtpAuth=").append(smtpAuth);
        sb.append(", smtpStarttls=").append(smtpStarttls);
        sb.append('}');
        return sb.toString();
    }

}
