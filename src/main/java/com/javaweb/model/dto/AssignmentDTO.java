package com.javaweb.model.dto;

import java.util.List;

public class AssignmentDTO {
    private Long Id;
    private List<Long> staffs;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public List<Long> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Long> staffs) {
        this.staffs = staffs;
    }
}
