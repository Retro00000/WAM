package com.XuanProject.service;

import com.XuanProject.entity.StudentDO;
import com.XuanProject.req.StudentRequest;
import com.XuanProject.res.TableDTO;

public interface StudentService {

    TableDTO retrieveStudents(StudentRequest request);

    boolean add(StudentDO studentDO);

    StudentDO getById(int selectedStudentId);

    boolean update(StudentDO studentDO);

    boolean delete(int[] selectedStudentIds);
}
