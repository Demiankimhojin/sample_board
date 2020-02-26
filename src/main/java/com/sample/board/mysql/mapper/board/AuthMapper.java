package com.sample.board.mysql.mapper.board;

import com.sample.board.mysql.model.board.AccountManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Description : Auth Query Mapper
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@Repository
@Mapper
public interface AuthMapper {
    boolean checkAccountDuplicate (@Param("loginId")String loginId);

    Long insertAccountManagements(@Param("loginId")String loginId,
                                  @Param("loginPwd")String loginPwd);

    AccountManagement getAccountInfo(@Param("loginId")String loginId);

    Integer authAccount(@Param("accountId")Long accountId);

    Integer authFailAccount(@Param("accountId")Long accountId);
}
