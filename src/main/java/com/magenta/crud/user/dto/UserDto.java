package com.magenta.crud.user.dto;

import com.magenta.crud.contract.Contract;
import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class UserDto implements Serializable {

    private int id;
    private String firstname;
    private String lastname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String passport;
    private String address;
    private String email;
    private String login;
    private String password;
    private Role role;
    private Status status;
    private List<Contract> numbers;
}
