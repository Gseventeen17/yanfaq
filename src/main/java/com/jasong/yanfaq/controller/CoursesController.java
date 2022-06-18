package com.jasong.yanfaq.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasong.yanfaq.commom.Result;
import com.jasong.yanfaq.entity.Courses;
import com.jasong.yanfaq.entity.Questions;
import com.jasong.yanfaq.entity.User;
import com.jasong.yanfaq.exception.ServiceException;
import com.jasong.yanfaq.service.ICoursesService;
import com.jasong.yanfaq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jasonG
 * @since 2022-05-04
 */
@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Resource
    private ICoursesService iCoursesService;

    @GetMapping
    public Result<?> findAll(){
        //查询所有
        List<Courses> coursesList = iCoursesService.list();
        //找出一级课程
        List<Courses> parentNode = coursesList.stream().filter(courses -> courses.getPid() == null).collect(Collectors.toList());
        for (Courses courses : parentNode) {
            List<Courses> children = coursesList.stream().filter(c -> courses.getId().equals(c.getPid())).collect(Collectors.toList());
            courses.setChildren(children);
        }
        return Result.success(parentNode);
    }

    @GetMapping("/getPid")
    public Result<?> getPid(@RequestParam String course){
        //查询pid
        LambdaQueryWrapper<Courses> wrapper= Wrappers.<Courses>lambdaQuery();
        wrapper.eq(Courses::getLabel, course);
        Courses one = iCoursesService.getOne(wrapper);
        Integer pid = one.getPid();
        return Result.success(pid);
    }

    //查询专业课
    @GetMapping("/getProfession")
    public Result<?> getProfession(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Courses> wrapper=Wrappers.<Courses>lambdaQuery();
        wrapper.like(Courses::getLabel,search).isNotNull(Courses::getPid).orderByDesc(Courses::getId);
        Page<Courses> page = new Page<>(pageNum, pageSize);
        Page<Courses> allCoursesPage = iCoursesService.page(page, wrapper);
        if (allCoursesPage.getSize()!=0) {
            return Result.success(allCoursesPage);
        }else {
            return Result.error("500","系统错误，获取信息失败");
        }

    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        boolean b = false;
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("A")){

                b = iCoursesService.removeById(id);
            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，删除失败");
            }

        }else
            return Result.error("401","权限不足");

    }

    @PutMapping
    public Result<?> update(@RequestBody Courses courses){
        boolean b;
        User currentUser = TokenUtils.getCurrentUser();
        if(currentUser.getRole().equals("A")){

                b = iCoursesService.updateById(courses);
            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，更新失败");
            }

        }else {
            return Result.error("401","权限不足");
        }
    }

    @PostMapping("/saveCourse")
    public Result<?> save(@RequestBody Courses courses) {
        User currentUser = TokenUtils.getCurrentUser();
        if(currentUser.getRole().equals("A")){
                boolean save = iCoursesService.save(courses);
            if (save) {
                return Result.success(save);
            }else {
                return Result.error("500","系统错误,添加失败");
            }

        }else {
            return Result.error("401","权限不足");
        }
    }

}
