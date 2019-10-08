package com.example.demo.service;

import com.example.demo.bean.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SysUserMapper userMapper;

    public void save(SysUser user){
        userMapper.insert(user);
    }

    public List<SysUser> findAll(){
        return userMapper.findAll();
    }

    public SysUser getUserByName(String name){
        return userMapper.getUserByName(name);
    }
}
