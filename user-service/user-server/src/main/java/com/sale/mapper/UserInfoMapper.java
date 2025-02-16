package com.sale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sale.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Update("UPDATE user_info SET avatar = #{avatar} WHERE username = #{username}")
    Integer updateAvatarByUsername(@Param("username") String username, @Param("avatar") String avatar);
}
