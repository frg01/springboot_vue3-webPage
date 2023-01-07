package com.hspedu.furn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hspedu.furn.bean.Furn;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: guorui fu
 * @versiion: 1.0
 * mybatis-plus 可以继承BaseMapper
 */
//@Mapper
public interface FurnMapper extends BaseMapper<Furn> {
    //有额外的方法 在该接口声明
}
