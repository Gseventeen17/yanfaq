package com.jasong.yanfaq.service.impl;

import com.jasong.yanfaq.entity.Questions;
import com.jasong.yanfaq.mapper.QuestionsMapper;
import com.jasong.yanfaq.service.IQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jasonG
 * @since 2022-04-23
 */
@Service
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions> implements IQuestionsService {

}
