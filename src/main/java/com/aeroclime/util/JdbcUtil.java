package com.aeroclime.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class JdbcUtil {

    private static String url;
    private static String username;
    private static String password;

    @Value("${spring.datasource.url}")
    private String cfgUrl;
    @Value("${spring.datasource.username}")
    private String cfgUsername;
    @Value("${spring.datasource.password}")
    private String cfgPassword;

    @PostConstruct
    private void init() {
        url = cfgUrl;
        username = cfgUsername;
        password = cfgPassword;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
