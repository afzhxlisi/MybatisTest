package me.gacl.domain;

import java.util.Date;

/**
 * @author gacl
 * users表所对应的实体类
 */
public class User {

    //实体类的属性和表的字段名称一一对应
    private int id;
    private Date startDate;
    private Date endDate;
    
    
    public Date getStartDate() {
        return startDate;
    }

    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", startDate=" + startDate + ", age=" + endDate + "]";
    }


    
    public Date getEndDate() {
        return endDate;
    }


    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}