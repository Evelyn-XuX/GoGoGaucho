package edu.ucsb.cs.cs184.gogogacho;

import java.io.Serializable;

public class Course implements Serializable {
    private String ClassName;
    private int id;
    private boolean taken;

    Course(){
        ClassName = "";
        id = -1;
        taken = false;
    }
    public Course(String cn, int id, boolean t){
        this.ClassName = cn;
        this.id = id;
        this.taken = t;
    }

    public void setClassName(String cn){
        ClassName = cn;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTaken(boolean t){taken = t;}

    public int getId(){return id;}
    public String getClassName(){return ClassName;}
    public boolean getTaken(){return taken;}

    @Override
    public String toString() {
        return "Course{" +
                "ClassName='" + ClassName + '\'' +
                ", id=" + id +
                ", taken=" + taken +
                '}';
    }
}
