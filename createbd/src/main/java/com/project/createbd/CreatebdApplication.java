package com.project.createbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
@EnableScheduling
public class CreatebdApplication {
    public static void main(String[] args){
        SpringApplication.run(CreatebdApplication.class, args);
    }
}
