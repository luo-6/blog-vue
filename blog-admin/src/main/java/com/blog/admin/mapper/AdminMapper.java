package com.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.admin.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from 'ms_permission' where id in(select permission_id from ms_admin_permission where admin_id = 1 )")
    public Admin findAdminByUserName(String username);
}
