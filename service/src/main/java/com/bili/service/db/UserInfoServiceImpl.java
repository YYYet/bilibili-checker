package com.bili.service.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.entity.mysql.User;
import com.bili.common.entity.mysql.UserDataHis;
import com.bili.dao.mapper.UserDataHisMapper;
import com.bili.dao.mapper.UserMapper;
import com.bili.common.entity.mysql.UserHisDataTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Service
public class UserInfoServiceImpl {
    @Resource
    UserMapper userMapper;
    @Resource
    UserDataHisMapper userDataHisMapper;

    public void recordUser2MySql(UserInfo userInfo){
        User user = new User();
        user.setUserName(userInfo.getUserName());
        user.setUid(userInfo.getUid());
        user.setAvatar(userInfo.getAvatar());
        User userFromDb = userMapper.selectOne(new QueryWrapper<User>().eq("uid", userInfo.getUid()));
//        boolean hasUid = userMapper.exists(new QueryWrapper<User>().eq("uid", userInfo.getUid()));
        if (userFromDb != null){
            UserDataHis userDataHis = new UserDataHis();
            userDataHis.setUid(userInfo.getUid());

            if (!userFromDb.getAvatar().equals(user.getAvatar()) && !userFromDb.getUserName().equals(user.getUserName())){
                userDataHis.setDataType(UserHisDataTypeEnum.HIS_AVATAR.name());
                userDataHis.setUserData(user.getAvatar());
                boolean exists = userDataHisMapper.exists(new QueryWrapper<UserDataHis>().eq("uid", userInfo.getUid()).eq("user_data", user.getAvatar()));
                if (!exists){
                    userDataHisMapper.insert(userDataHis);
                }

                userDataHis.setId(null);
                userDataHis.setDataType(UserHisDataTypeEnum.HIS_NAME.name());
                userDataHis.setUserData(user.getUserName());
                exists = userDataHisMapper.exists(new QueryWrapper<UserDataHis>().eq("uid", userInfo.getUid()).eq("user_data", user.getUserName()));
                if (!exists){
                    userDataHisMapper.insert(userDataHis);
                }
            }else {
                if (!userFromDb.getAvatar().equals(user.getAvatar())){
                    userDataHis.setDataType(UserHisDataTypeEnum.HIS_AVATAR.name());
                    userDataHis.setUserData(user.getAvatar());
                    boolean exists = userDataHisMapper.exists(new QueryWrapper<UserDataHis>().eq("uid", userInfo.getUid()).eq("user_data", user.getAvatar()));
                    if (!exists){
                        userDataHisMapper.insert(userDataHis);
                    }
                }
                userDataHis.setId(null);
                if (!userFromDb.getUserName().equals(user.getUserName())){
                    userDataHis.setDataType(UserHisDataTypeEnum.HIS_NAME.name());
                    userDataHis.setUserData(user.getUserName());
                    boolean exists = userDataHisMapper.exists(new QueryWrapper<UserDataHis>().eq("uid", userInfo.getUid()).eq("user_data", user.getUserName()));
                    if (!exists){
                        userDataHisMapper.insert(userDataHis);
                    }
                }
            }



        }else {
            userMapper.insert(user);
        }

    }
}
