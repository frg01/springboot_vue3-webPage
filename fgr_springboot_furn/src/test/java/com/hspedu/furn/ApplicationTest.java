package com.hspedu.furn;

import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.mapper.FurnMapper;
import com.hspedu.furn.service.FurnService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@SpringBootTest
public class ApplicationTest {

    @Resource
    private FurnMapper furnMapper;

    @Resource
    private FurnService furnService;

    @Test
    public void testFurnMapper(){
        System.out.println("furnMapper--" + furnMapper.getClass());
        Furn furn = furnMapper.selectById(1);
        System.out.println("furn--" + furn);
    }

    @Test
    public void testFurnService(){
        Furn furn = furnService.getById(1);
        System.out.println("furn--" + furn);
    }
}
