package com.dhcc.demo.module.menu.service.impl;

import com.dhcc.demo.module.menu.entity.Menu;
import com.dhcc.demo.module.menu.mapper.MenuMapper;
import com.dhcc.demo.module.menu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guogang
 * @since 2020-12-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public  IPage<Menu> findListByPage(Integer page, Integer pageCount){
        IPage<Menu> wherePage = new Page<>(page, pageCount);
        Menu where = new Menu();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Menu menu){
        return baseMapper.insert(menu);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Menu menu){
        return baseMapper.updateById(menu);
    }

    @Override
    public Menu findById(Long id){
        return  baseMapper.selectById(id);
    }
}
