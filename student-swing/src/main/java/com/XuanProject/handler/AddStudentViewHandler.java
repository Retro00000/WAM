package com.XuanProject.handler;

import com.XuanProject.student.view.AddStudentView;
import com.XuanProject.student.view.MainView;
import com.XuanProject.entity.StudentDO;
import com.XuanProject.service.StudentService;
import com.XuanProject.service.impl.StudentServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentViewHandler implements ActionListener {

    private AddStudentView addStudentView;
    private MainView mainView;
    public AddStudentViewHandler(AddStudentView addStudentView, MainView mainView) {
        this.addStudentView = addStudentView;
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        if ("添加".equals(text)) {
            StudentService studentService = new StudentServiceImpl();
            StudentDO studentDO = addStudentView.buildStudentDO();
            boolean addResult = studentService.add(studentDO);
            if (addResult) {
                // 重新加载表格查到最新数据
                mainView.reloadTable();
                addStudentView.dispose();
            } else {
                JOptionPane.showMessageDialog(addStudentView,"添加失败");
            }
        }
    }
}
