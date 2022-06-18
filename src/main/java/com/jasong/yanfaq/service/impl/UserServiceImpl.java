package com.jasong.yanfaq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jasong.yanfaq.controller.dto.UserDTO;
import com.jasong.yanfaq.entity.User;
import com.jasong.yanfaq.exception.ServiceException;
import com.jasong.yanfaq.mapper.UserMapper;
import com.jasong.yanfaq.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasong.yanfaq.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jasonG
 * @since 2022-04-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);

        }catch (Exception e){
            throw new ServiceException("500","系统错误");
        }
        if(one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException("600","用户名或密码错误");
        }
    }
}
