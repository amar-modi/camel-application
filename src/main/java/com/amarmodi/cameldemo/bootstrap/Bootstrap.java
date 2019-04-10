package com.amarmodi.cameldemo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class Bootstrap implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        jdbcTemplate.execute("Create table if not exists INPUT_POSTS (id numeric, name varchar(255));");
        jdbcTemplate.execute("Create table if not exists COUNTRY (country_i serial,name text    , country_code text, population numeric, crte_ts timestamp default now());");
    }
}
