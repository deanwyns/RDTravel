package com.realdolmen.rdtravel.persistence;

public enum DatabaseEngine {
    mysql("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/rdtravel", "root", "root"),
    h2("org.h2.Driver", "jdbc:h2:mem:rdtravel", "sa", "");

    public final String url;
    public final String username;
    public final String driverClass;
    public final String password;

    DatabaseEngine(String driverClass, String url, String username, String password) {
        this.password = password;
        this.driverClass = driverClass;
        this.username = username;
        this.url = url;
    }
}
