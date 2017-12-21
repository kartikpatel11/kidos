package com.example.kidos.beans;

/**
 * Created by Kartik on 09/12/17.
 */

public class KidosRegistrationBean {

    private String firstname;
    private String lastname;
    private String emailid;
    private String mobile;
    private String password;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "KidosPartnersRegistrationDetailsBean{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailid='" + emailid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
