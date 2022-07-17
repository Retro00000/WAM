package com.XuanProject.service.impl;

import com.XuanProject.entity.AdminDO;
import com.XuanProject.service.AdminService;
import com.XuanProject.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminServiceImpl implements AdminService {
    @Override
    public boolean validateAdmin(AdminDO adminDO) {
        String userName = adminDO.getUserName();
        String pwdParam = adminDO.getPwd();
        String sql = "select pwd from manager where user_name = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = DBUtil.getConn();
            if (conn == null) {
                return false;
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String pwd = resultSet.getString(1);
                if (pwdParam.equals(pwd)) {
                    return true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeRs(resultSet);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }
}
