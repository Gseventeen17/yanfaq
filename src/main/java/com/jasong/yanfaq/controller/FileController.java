package com.jasong.yanfaq.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jasong.yanfaq.commom.Result;
import com.jasong.yanfaq.entity.Files;
import com.jasong.yanfaq.entity.Questions;
import com.jasong.yanfaq.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileMapper fileMapper;
    @Value("${file.upload.url}")
    private String fileUploadPath;

    @PostMapping("upload")
    public Result<?> upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9090/file/" + fileUUID;
        }


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        return Result.success(saveFile);
    }

    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File file2 = new File(fileUploadPath + fileUUID);
        ServletOutputStream outputStream = response.getOutputStream();

        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        outputStream.write(FileUtil.readBytes(file2));
        outputStream.flush();
        outputStream.close();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id){
        Files files = fileMapper.selectById(id);
        LambdaQueryWrapper<Files> wrapper=Wrappers.<Files>lambdaQuery();
        wrapper.eq(Files::getUrl, files.getUrl());
        List<Files> filesList = fileMapper.selectList(wrapper);
        if (filesList.size()==1) {
            String[] split = files.getUrl().split("/");
            File deletedFile = new File(fileUploadPath+split[4]);
           deletedFile.delete();
        }
        int i = fileMapper.deleteById(id);


        return Result.success(i);
    }
}
