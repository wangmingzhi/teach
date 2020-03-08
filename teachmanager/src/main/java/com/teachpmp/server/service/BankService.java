package com.teachpmp.server.service;

import com.teachpmp.server.dao.BankInfoMapper;
import com.teachpmp.server.dao.RoleMapper;
import com.teachpmp.server.entity.BankInfo;
import com.teachpmp.server.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService extends AbstractBaseService<BankInfo>{

    private final BankInfoMapper bankInfoMapper;
    @Autowired
    public BankService(BankInfoMapper bankInfoMapper) {
        super(bankInfoMapper);
        this.bankInfoMapper = bankInfoMapper;
    }


    /**
     * 根据 trainType exerciseType  bankName 查询题库
     * @param bankInfo
     * @return
     */
    public BankInfo getBank(BankInfo bankInfo){
        return bankInfoMapper.getBank(bankInfo);
    }

    public List<BankInfo> getBanks(BankInfo bankInfo){
        return bankInfoMapper.page(bankInfo);
    }

    public void updateExerciseNum(String bankId, int num){
        bankInfoMapper.updateExerciseNum(bankId, num);
    }
}
