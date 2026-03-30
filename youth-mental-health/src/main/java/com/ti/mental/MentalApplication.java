package com.ti.mental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 青少年心理健康管理系统 - 启动类
 *
 * @author Ti
 * @date 2026-02-03
 */
@SpringBootApplication
@MapperScan("com.ti.mental.mapper")
public class MentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentalApplication.class, args);
        System.out.println("====================================");
        System.out.println("  大学生心理健康管理系统启动成功！");
        System.out.println("  API文档: http://localhost:8888/doc.html");
        System.out.println("====================================");
    }
}
