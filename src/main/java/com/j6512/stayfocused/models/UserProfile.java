package com.j6512.stayfocused.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class UserProfile extends AbstractEntity {

    private String firstName;

    private String lastName;

    private String location;


//    private Date birthday;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    public UserProfile() {
    }

    public UserProfile(String firstName, String lastName, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
//        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }


}
