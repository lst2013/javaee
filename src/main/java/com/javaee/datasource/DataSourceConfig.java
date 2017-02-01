package com.javaee.datasource;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;

@DataSourceDefinition(name = "java:app/javaee/javaeeDS", className = "org.h2.jdbcx.JdbcDataSource", url = "jdbc:h2:mem:javaee")
@Singleton
public class DataSourceConfig {
}