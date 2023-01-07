package com.hspedu.furn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.service.FurnService;
import com.hspedu.furn.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: guorui fu
 * @versiion: 1.0
 * 前后端分离，默认情况下，前端发出请求，后端返回json数据 使用符合注解@RestController
 */
@RestController
@Slf4j
public class FurnController {

    @Resource
    private FurnService furnService;

    //编写方法，完成添加
    //前端是以json格式发送添加信息 需要使用@RequestBody 才能将数据封装到bean
    //如果是以表单形式提交 则不需要使用@RequestBody
    //Content-Type要与提交数据的格式对应
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Furn furn, Errors errors) {
        //如果出现校验错误，会把校验错误封装到errors中
        //定义map把errors中校验错误放入map，有错误就不提交 ，错误信息返回个客户端
        Map<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        //遍历 如果有就放入map中 有或没有
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        if (map.isEmpty()) {//没有校验错误
            log.info("furn--", furn);
            furnService.save(furn);
            return Result.success();//返回成功
        }else {
            return Result.error("400","后端校验失败",map);
        }
    }

    //返回所有家居信息
    @RequestMapping(value = "/furns", method = RequestMethod.POST)
    public Result listFurns() {
        List<Furn> furns = furnService.list();
        return Result.success(furns);
    }

    /**
     * 1.修改的请求，使用PUT
     * 2.@RequestBody 表示修改的数据是以json格式发送的
     *
     * @param furn
     * @return
     */
    @PutMapping(value = "/update")
    public Result updateFurn(@RequestBody Furn furn) {
        furnService.updateById(furn);
        return Result.success();
    }

    //通过id查询家居
    @GetMapping(value = "/furn/{id}")
    public Result getFurnById(@PathVariable(value = "id") Integer id) {
        Furn furn = furnService.getById(id);
//        System.out.println("查询getFurnById" + furn);
        return Result.success(furn);
    }

    //处理删除
    @DeleteMapping(value = "/del/{id}")
    public Result del(@PathVariable(value = "id") Integer id) {
        furnService.removeById(id);
        return Result.success();
    }

    //分页查询的接口、方法
    @GetMapping("/furnsByPage")
    public Result lisFurnByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "5") Integer pageSize
    ) {
        //通过page方法返回page对象，对象中封装了分页数据
        Page<Furn> page = furnService.page(new Page<Furn>(pageNum, pageSize));
        //返回的page数据结构是如何的
        return Result.success(page);
    }


    /**
     * 支持带条件分页检索
     *
     * @param pageNum  第几页
     * @param pageSize 每页几条记录
     * @param search   检索条件 家居名 默认”“ 白=表示不带条件检索
     * @return
     */
    @GetMapping("/furnsBySearchPage")
    public Result listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "5") Integer pageSize,
                                           @RequestParam(defaultValue = "") String search) {
        //创建一个QueryWrapper 封装检索条件
        QueryWrapper<Furn> queryWrapper = Wrappers.query();
        //判断search是否为null  str != null && !str.isEmpty() && containsText(str)
        if (StringUtils.hasText(search)) {
            queryWrapper.like("name", search);
        }
        Page<Furn> page = furnService.page(new Page<Furn>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    //编写方法 使用LambdaQueryWrapper 封装查询条件 完成检索
    //把 Furn::getName 赋给了SFunction<T,R> 函数式接口
    /*源码：
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable {
    }
    父接口： R apply(T t); 根据类型T的参数，获取R的结果
    后面还有默认实现方法
    Furn::getName 后 就相当于实现了SFunction 的apply方法
    底层会根据传入的Furn::getName方法 去得到该方法对应的属性映射的表的字段
    xxMapper.xml中有ResultMap 会体现Bean的属性和表的字段的映射关系
     */
    @GetMapping("/furnsBySearchPage2")
    public Result listFurnsByConditionPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "5") Integer pageSize,
                                           @RequestParam(defaultValue = "") String search) {
        //创建一个LambdaQueryWrapper 封装检索条件
        LambdaQueryWrapper<Furn> lambdaQueryWrapper = Wrappers.<Furn>lambdaQuery();

        //判断search
        if (StringUtils.hasText(search)){
            lambdaQueryWrapper.like(Furn::getName,search);
        }

        Page<Furn> page = furnService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
        log.info("page={}",page.getRecords());
        return Result.success(page);
    }

}
