package com.superafroman.mangopay.domain;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class User implements Serializable {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Email")
    private String emailAddress;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Nationality")
    private String nationality;

    @JsonProperty("Birthday")
    private Long birthday;

    @JsonProperty("PersonType")
    private PersonType personType = PersonType.NATURAL_PERSON;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("CreationDate")
    private Long creationDate;

    @JsonProperty("UpdateDate")
    private Long updateDate;

    @JsonProperty("Tag")
    private String tag;

    @JsonProperty("HasRegisteredMeansOfPayment")
    private Boolean hasRegisteredMeansOfPayment;

    @JsonProperty("CanRegisterMeanOfPayment")
    private Boolean canRegisterMeanOfPayment;

    @JsonProperty("IsStrongAuthenticated")
    private Boolean isStrongAuthenticated;

    @JsonProperty("PersonalWalletAmount")
    private Double personalWalletAmount;


    public User() {
    }

    public User(String firstName, String lastName, String emailAddress,
            String nationality, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.nationality = nationality;
        this.birthday = birthday.getTime() / 1000;
    }

    public Long getBirthday() {
        return birthday;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIp() {
        return ip;
    }

    public String getLastName() {
        return lastName;
    }


    public String getNationality() {
        return nationality;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public Long getId() {
        return id;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public String getTag() {
        return tag;
    }

    public Boolean isHasRegisteredMeansOfPayment() {
        return hasRegisteredMeansOfPayment;
    }

    public Boolean isCanRegisterMeanOfPayment() {
        return canRegisterMeanOfPayment;
    }

    public Boolean isStrongAuthenticated() {
        return isStrongAuthenticated;
    }

    @Override
    public String toString() {
        return String.format("[User id = %s, firstName = %s, lastName = %s]",
                id.toString(), firstName, lastName);
    }
}
