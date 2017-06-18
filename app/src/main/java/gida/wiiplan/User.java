package gida.wiiplan;

/**
 * Created by Zacharia Manyoni on 2016/11/04.
 */

public class User {

    private String varsity_num,fname,lname;

    public User(String varsity_num, String fname, String lname){
        setVarsity_num(varsity_num);
        setFname(fname);
        setLname(lname);
    }

    public String getVarsity_num() {
        return varsity_num;
    }

    public void setVarsity_num(String varsity_num) {
        this.varsity_num = varsity_num;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return getFname()+" "+getLname();
    }
}
