package com.jasong.yanfaq.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasong.yanfaq.commom.Result;
import com.jasong.yanfaq.entity.Files;
import com.jasong.yanfaq.entity.Questions;
import com.jasong.yanfaq.entity.User;
import com.jasong.yanfaq.exception.ServiceException;
import com.jasong.yanfaq.mapper.FileMapper;
import com.jasong.yanfaq.service.IQuestionsService;
import com.jasong.yanfaq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Wrapper;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jasonG
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/questions")
public class QuestionsController {
    @Autowired
    IQuestionsService iQuestionsService;
    @Resource
    private FileMapper fileMapper;
    @Value("${file.upload.url}")
    private String fileUploadPath;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam String search,@RequestParam String sort) {
        LambdaQueryWrapper<Questions> wrapper=Wrappers.<Questions>lambdaQuery();
//        if(StrUtil.isNotBlank(search)){
            wrapper.like(Questions::getQues, search);
//        }
        if(sort.equals("createdTime")){
            wrapper.orderByDesc(Questions::getCreatedTime);
        }else {
            wrapper.orderByDesc(Questions::getUpdateTime);
        }
        Page<Questions> page = new Page<>(pageNum, pageSize);

        Page<Questions> questionsPage;

            questionsPage = iQuestionsService.page(page, wrapper);
        if (questionsPage.getSize()!=0) {
            return Result.success(questionsPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }
    }

    @GetMapping("/bySubject")
    public Result<?> bySubject(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam String search,@RequestParam String sort) {
        LambdaQueryWrapper<Questions> wrapper=Wrappers.<Questions>lambdaQuery();
//        if(StrUtil.isNotBlank(search)){
        wrapper.like(Questions::getSubject, search);
//        }
        if(sort.equals("createdTime")){
            wrapper.orderByDesc(Questions::getCreatedTime);
        }else {
            wrapper.orderByDesc(Questions::getUpdateTime);
        }
        Page<Questions> page = new Page<>(pageNum, pageSize);

        Page<Questions> questionsPage;

        questionsPage = iQuestionsService.page(page, wrapper);
        if (questionsPage.getSize()!=0) {
            return Result.success(questionsPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }

    }

    @GetMapping("/myquestions")
    public Result<?> findMyPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam String search) {
        String nickName = TokenUtils.getCurrentUser().getNickName();
        LambdaQueryWrapper<Questions> wrapper=Wrappers.<Questions>lambdaQuery();
        wrapper.eq(Questions::getAuthor, nickName)
                .like(Questions::getQues,search)
                .orderByDesc(Questions::getCreatedTime);
        Page<Questions> page = new Page<>(pageNum, pageSize);
        Page<Questions> questionsPage;

            questionsPage = iQuestionsService.page(page, wrapper);
        if (questionsPage.getSize()!=0) {
            return Result.success(questionsPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }

    }

    @GetMapping("/tobesolved")
    public Result<?> findToBeSolved(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam String search) {
        String subject = TokenUtils.getCurrentUser().getSubject();
        LambdaQueryWrapper<Questions> wrapper=Wrappers.<Questions>lambdaQuery();
        wrapper.eq(Questions::getSubject, subject)
                .eq(Questions::getSolved,"f")
                .like(Questions::getQues,search)
                .orderByDesc(Questions::getCreatedTime);
        Page<Questions> page = new Page<>(pageNum, pageSize);
        Page<Questions> questionsPage;

            questionsPage = iQuestionsService.page(page, wrapper);
        if (questionsPage.getSize()!=0) {
            return Result.success(questionsPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }

    }

    @PutMapping
    public Result<?> update(@RequestBody Questions questions){
        boolean b;
        Questions question = iQuestionsService.getById(questions.getId());
        if(question.getAnsurl()!=null&&questions.getAnsurl()==null){
            doubleDelete(question.getAnsurl());
        }
        User currentUser = TokenUtils.getCurrentUser();

        if (currentUser.getRole().equals("T")) {
            questions.setSolved("t");
            questions.setTeacher(currentUser.getNickName());
            questions.setUpdateTime(DateUtil.date(Calendar.getInstance()));

                b=iQuestionsService.updateById(questions);
            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，更新失败");
            }



        }else {
            return Result.error("401","权限不足无法更新");
        }
    }

    @GetMapping("/solved")
    public Result<?> findSolved(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam String search) {
        User user = TokenUtils.getCurrentUser();
        LambdaQueryWrapper<Questions> wrapper=Wrappers.<Questions>lambdaQuery();
        wrapper.eq(Questions::getTeacher, user.getNickName())
//                .eq(Questions::getSolved,"t")
                .like(Questions::getQues,search)
                .orderByDesc(Questions::getUpdateTime);
        Page<Questions> page = new Page<>(pageNum, pageSize);
        Page<Questions> questionsPage;

            questionsPage = iQuestionsService.page(page, wrapper);
        if (questionsPage.getSize()!=0) {
            return Result.success(questionsPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }

    }

    @GetMapping("/getOne")
    public Result<?> getOne(@RequestParam Long id) {
        Questions question;

            question = iQuestionsService.getById(id);
        if (question != null) {
            return Result.success(question);
        }else {
            return Result.error("500","系统错误，问题不存在");
        }



    }




    @PostMapping("/newquestions")
    public Result<?> save(@RequestBody Questions questions) {
        String nickName = TokenUtils.getCurrentUser().getNickName();
        questions.setAuthor(nickName);
        questions.setSolved("f");
        questions.setCreatedTime(DateUtil.date(Calendar.getInstance()));
        boolean save = iQuestionsService.save(questions);;
        if (save) {
            return Result.success(save);
        }else {
            return Result.error("500","系统错误，更新失败");
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        boolean b = false;
        Questions question = iQuestionsService.getById(id);
        if (question != null) {
            //本地删除

                doubleDelete(question.getQuesurl());


                doubleDelete(question.getAnsurl());



            b = iQuestionsService.removeById(id);

            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，删除失败");
            }
        }else {
            return Result.error("500","系统错误，问题不存在");
        }

    }


    public void doubleDelete(String url){
        LambdaQueryWrapper<Files> wrapper=Wrappers.<Files>lambdaQuery();
            wrapper.eq(Files::getUrl, url);
            List<Files> filesList = fileMapper.selectList(wrapper);
            if (filesList.size()==1) {
                String[] split = url.split("/");
                File deletedFile = new File(fileUploadPath+split[4]);
                deletedFile.delete();
            }
            if(filesList.size()!=0){
                fileMapper.deleteById(filesList.get(0).getId());
            }

    }
}
