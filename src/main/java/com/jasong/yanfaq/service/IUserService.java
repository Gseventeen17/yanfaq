package com.jasong.yanfaq.service;

import com.jasong.yanfaq.controller.dto.UserDTO;
import com.jasong.yanfaq.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jasonG
 * @since 2022-04-22
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);
}
