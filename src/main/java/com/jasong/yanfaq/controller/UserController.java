package com.jasong.yanfaq.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasong.yanfaq.commom.Result;
import com.jasong.yanfaq.controller.dto.UserDTO;
import com.jasong.yanfaq.entity.Pass;
import com.jasong.yanfaq.entity.Terms;
import com.jasong.yanfaq.entity.User;
import com.jasong.yanfaq.service.IQuestionsService;
import com.jasong.yanfaq.service.IUserService;
import com.jasong.yanfaq.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jasonG
 * @since 2022-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService iUserService;

    @Autowired
    IQuestionsService iQuestionsService;

    //用户注册
    @PostMapping("/register")
    public Result<?> save(@RequestBody User user) {
        String username = user.getUsername();
        String nickName = user.getNickName();

        LambdaQueryWrapper<User> wrapperU = Wrappers.<User>lambdaQuery();
        wrapperU.eq(User::getUsername, username);
        long countU = iUserService.count(wrapperU);

        LambdaQueryWrapper<User> wrapperN = Wrappers.<User>lambdaQuery();
        wrapperN.eq(User::getNickName, nickName);
        long countN = iUserService.count(wrapperN);

        if (countU != 0) {
            return Result.error("-1", "注册失败，用户名重复");
        } else if (countN != 0) {
            return Result.error("-1", "注册失败，昵称重复");
        } else {
            user.setRegisterTime(DateUtil.date(Calendar.getInstance()));
            boolean save = iUserService.save(user);
            if (save) {
                return Result.success();
            }else {
                return Result.error("500","系统错误，注册失败");
            }
        }

    }

    //用户管理
//    @GetMapping ("/all")
//    public Result<?> findAllUser(@RequestParam(defaultValue = "1") Integer pageNum,
//                                 @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search,@RequestParam String role) {
//        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
//        wrapper.like(User::getNickName,search).eq(User::getRole,role).orderByDesc(User::getId);
//        Page<User> page = new Page<>(pageNum, pageSize);
//        Page<User> allUserPage = iUserService.page(page, wrapper);
//        return Result.success(allUserPage);
//    }

    @PostMapping("/all")
    public Result<?> findAllUser(@RequestBody Terms terms) {
        User currentUser = TokenUtils.getCurrentUser();
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.like(User::getNickName, terms.getSearch())
                .eq(User::getRole, terms.getRole());
        Page<User> page = new Page<>(terms.getPageNum(), terms.getPageSize());
        if (currentUser.getRole().equals("A")) {
            Page<User> allUserPage = iUserService.page(page, wrapper);
            if (allUserPage.getSize() != 0) {
                return Result.success(allUserPage);
            }else {
                return Result.error("500","系统错误，信息获取失败");
            }

        } else {
            return Result.error("401", "权限不足");
        }
    }

    @PostMapping("/allTeacher")
    public Result<?> findAllTeacher(@RequestBody Terms terms) {
        User currentUser = TokenUtils.getCurrentUser();
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.like(User::getNickName, terms.getSearch())
                .like(User::getSubject, terms.getSubject())
                .eq(User::getRole, terms.getRole());
        Page<User> page = new Page<>(terms.getPageNum(), terms.getPageSize());
        if (currentUser.getRole().equals("A")) {
            Page<User> allUserPage = iUserService.page(page, wrapper);
            if (allUserPage.getSize() != 0) {
                return Result.success(allUserPage);
            }else {
                return Result.error("500","系统错误，信息获取失败");
            }
        } else {
            return Result.error("401", "权限不足");
        }
    }


    @PutMapping
    public Result<?> update(@RequestBody User user) {
        boolean b;
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("U") || currentUser.getRole().equals("T")) {
            if (currentUser.getId().equals(user.getId())) {
                user.setUpdateTime(DateUtil.date(Calendar.getInstance()));
                b = iUserService.updateById(user);
                if (b) {
                    return Result.success(b);
                }else {
                    return Result.error("500","系统错误，更新失败");
                }


            } else {
                return Result.error("401", "权限不足，或者你正在修改他人信息");
            }
        } else if (currentUser.getRole().equals("A")) {
            if(Objects.equals(user.getRole(), "U")){
                user.setSubject(null);
            }
            user.setUpdateTime(DateUtil.date(Calendar.getInstance()));

            b = iUserService.updateById(user);
            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，更新失败");
            }

        } else {
            return Result.error("401", "权限不足");
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        boolean b = false;
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("A")) {

            b = iUserService.removeById(id);
            if (b) {
                return Result.success(b);
            }else {
                return Result.error("500","系统错误，删除失败");
            }

        } else
            return Result.error("401", "权限不足");
    }


    //用户校验
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserDTO userDTO) {
        UserDTO login = iUserService.login(userDTO);
        return Result.success(login);

    }

    @PostMapping("/currentUser")
    public Result<?> currentUser() {
        UserDTO userDTO = new UserDTO();
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser!=null) {
            BeanUtil.copyProperties(currentUser,userDTO,true);
            userDTO.setPassword("");
            return Result.success(userDTO);
        }else {
            return Result.error("500", "系统错误，信息获取失败");
        }

    }

    @PostMapping("/updatePass")
    public Result<?> updatePass(@RequestBody Pass pass) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getPassword().equals(pass.getOldPass())) {
            LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate();
            wrapper.set(User::getPassword, pass.getNewPass());
            wrapper.eq(User::getId, currentUser.getId());
            boolean update = false;
            update = iUserService.update(wrapper);
            if (update) {
                return Result.success(update);
            }else {
                return Result.error("500","系统错误，更新失败");
            }


        } else {
            return Result.error("-1", "原密码错误");
        }
    }
}
