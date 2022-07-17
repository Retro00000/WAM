package com.XuanProject.student.view;

import com.XuanProject.handler.MainViewHandler;
import com.XuanProject.req.StudentRequest;
import com.XuanProject.res.TableDTO;
import com.XuanProject.service.StudentService;
import com.XuanProject.service.impl.StudentServiceImpl;
import com.XuanProject.student.view.ext.MainViewTable;
import com.XuanProject.student.view.ext.MainViewTableModel;
import com.XuanProject.util.DimensionUtil;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class MainView extends JFrame{
    JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton addBtn = new JButton("增加");
    JButton updateBtn = new JButton("修改");
    JButton delBtn = new JButton("删除");
    JTextField searchTxt = new JTextField(15);
    JButton searchBtn = new JButton("查询");

    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton preBtn = new JButton("上一页");
    JButton nextBtn = new JButton("下一页");

    MainViewTable mainViewTable = new MainViewTable();
    private int pageNow = 1; // 当前是第几页
    private int pageSize = 10; // 一页显示多少条记录

    MainViewHandler mainViewHandler;
    public MainView() {
        super("学生成绩管理系统");
        Container contentPane = getContentPane();
        Rectangle bounds = DimensionUtil.getBounds();
        pageSize = Math.floorDiv(bounds.height,35);

        mainViewHandler = new MainViewHandler(this);
        // 放置北边的组件
        layoutNorth(contentPane);
        // 设置中间的jtable
        layoutCenter(contentPane);

        //放置南边的组件
        layoutSouth(contentPane);

        // 自定义图标
        URL imgUrl = MainView.class.getClassLoader().getResource("barbara.jpg");
        setIconImage(new ImageIcon(imgUrl).getImage());
        // 根据屏幕大小设置主界面大小
        setBounds(bounds);
        // 设置窗体完全充满整个屏幕的可见大小
        setSize(1300,700);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    private void layoutCenter(Container contentPane) {
        TableDTO dto = getTableDTO();
        MainViewTableModel mainViewTableModel = MainViewTableModel.assembleModel(dto.getData());
        // 把jtable和model关联
        mainViewTable.setModel(mainViewTableModel);
        mainViewTable.renderRule();
        JScrollPane jScrollPane = new JScrollPane(mainViewTable);
        contentPane.add(jScrollPane,BorderLayout.CENTER);
        showPreNext(dto.getTotalCount());
    }

    private TableDTO getTableDTO() {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest request = new StudentRequest();
        request.setPageNow(pageNow);
        request.setPageSize(pageSize);
        request.setSearchKey(searchTxt.getText().trim());
        TableDTO tableDTO = studentService.retrieveStudents(request);
        return tableDTO;
    }

    private void layoutSouth(Container contentPane) {
        preBtn.addActionListener(mainViewHandler);
        nextBtn.addActionListener(mainViewHandler);
        southPanel.add(preBtn);
        southPanel.add(nextBtn);
        contentPane.add(southPanel,BorderLayout.SOUTH);
    }
    /*
    设置上一页下一页是否可见
     */
    private void showPreNext(int totalCount) {
        if (pageNow == 1) {
            preBtn.setVisible(false);
        } else {
            preBtn.setVisible(true);
        }
        int pageCount = 0;//总共有多少页
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }
        if (pageNow == pageCount) {
            nextBtn.setVisible(false);
        } else {
            nextBtn.setVisible(true);
        }
    }

    private void layoutNorth(Container contentPane) {
        // 增加事件监听
        addBtn.addActionListener(mainViewHandler);
        updateBtn.addActionListener(mainViewHandler);
        delBtn.addActionListener(mainViewHandler);
        searchBtn.addActionListener(mainViewHandler);
        northPanel.add(addBtn);
        northPanel.add(updateBtn);
        northPanel.add(delBtn);
        northPanel.add(searchTxt);
        northPanel.add(searchBtn);
        contentPane.add(northPanel,BorderLayout.NORTH);
    }


    public static void main(String[] args) {
        new MainView();
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void reloadTable() {
        TableDTO dto = getTableDTO();
        MainViewTableModel.updateModel(dto.getData());
        mainViewTable.renderRule();
        showPreNext(dto.getTotalCount());
    }

    public int[] getSelectedStudentIds() {
        int[] selectedRows = mainViewTable.getSelectedRows();
        int[] ids = new int[selectedRows.length];
        for (int i = 0; i < selectedRows.length ;i++) {
            int rowIndex = selectedRows[i];
            Object idObj = mainViewTable.getValueAt(rowIndex, 0);
            ids[i] = Integer.valueOf(idObj.toString());
        }
        return ids;
    }
}
