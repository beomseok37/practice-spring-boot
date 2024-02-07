package dev.beomseok.boardserver.mapper;

import dev.beomseok.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserProfileMapper {
    public UserDTO getUserProfile(@Param("userId") String userId);

    int insertUserProfile(@Param("id") String id, @Param(("userId")) String userId,
                          @Param("password") String password,@Param("nickname") String nickname,
                          @Param("isAdmin") Boolean isAdmin, @Param("isWithDraw") Boolean isWithDraw,
                          @Param("createTime") Date createTime, @Param("updateTime") Date updateTime,
                          @Param("status") UserDTO.Status status);

    int deleteUserProfile(@Param("id") String id);

    public UserDTO findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(@Param("userId") String userId);

    int updatePassword(UserDTO user);

    int updateAddress(UserDTO user);

    int register(UserDTO userProfile);
}
