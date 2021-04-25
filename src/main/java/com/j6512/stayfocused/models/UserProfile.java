package com.j6512.stayfocused.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class UserProfile extends AbstractEntity {

    @NotBlank(message = "Please enter your first name.")
    @Size(min = 3, max = 12, message = "Your name must be between 3 and 12 characters.")
    private String firstName;

    @NotBlank(message = "Please enter your last name.")
    @Size(min = 3, max = 12, message = "Your name must be between 3 and 12 characters.")
    private String lastName;

    @Size(max = 20, message = "Your location can not be longer than 20 characters.")
    private String location;

    @Size(max = 500, message = "Your bio can not be longer than 500 characters.")
    private String bio;

    private Date birthday;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    public UserProfile(String firstName, String lastName, String location, String bio, Date birthday, User user) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.bio = bio;
        this.birthday = birthday;
        this.user = user;
    }

    public UserProfile() {
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
