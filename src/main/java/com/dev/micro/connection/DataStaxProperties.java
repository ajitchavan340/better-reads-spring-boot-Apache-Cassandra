package com.dev.micro.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties(prefix = "datastax.astra")
public class DataStaxProperties {

    private File SecureConnectBundle;

    public File getSecureConnectBundle() {
        return SecureConnectBundle;
    }

    public void setSecureConnectBundle(File secureConnectBundle) {
        SecureConnectBundle = secureConnectBundle;
    }
}
