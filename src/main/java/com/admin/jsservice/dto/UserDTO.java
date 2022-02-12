package com.admin.jsservice.dto;

import com.admin.jsservice.validation.ValidEmail;
import com.admin.jsservice.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ValidEmail
    @Column(name = "email",unique = true)
    private String email;

    @ValidPassword
    @Column(name = "password",nullable = false)
    private String password;

    @ValidPassword
    @Column(name = "confirm_password",nullable = false)
    private String confirmPassword;

    @Column(name = "full_name",nullable = false)
    private String fullName;

    @Column(name = "country",nullable = false)
    private String country;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "district",nullable = false)
    private String district;

    @Column(name = "aadhaar",nullable = false)
    private String aadhaar;

    @Column(name = "pan_card",nullable = false)
    private String panCard;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "mobile_no")
    private String mobileNo;
}
