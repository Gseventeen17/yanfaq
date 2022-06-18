package com.jasong.yanfaq.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class testGenerator {

    public static void main(String[] args) {
        // 代码生成器
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/qblog?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=GMT%2B8", "root", "0625")
                .globalConfig(builder -> {
                    builder.author("jasonG") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\graduation\\yanfaq\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.jasong") // 设置父包名
                            .moduleName("yanfaq") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\graduation\\yanfaq\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()
                                    .enableRestStyle();
                    builder.addInclude("courses") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    }

