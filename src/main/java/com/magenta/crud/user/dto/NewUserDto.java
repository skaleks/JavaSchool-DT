package com.magenta.crud.user.dto;

import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class NewUserDto {

    @NotEmpty(message = "Should not be empty")
    @Size(min = 2, max = 30, message = "Min = 2, Max = 30")
    private String firstname;

    @NotEmpty(message = "Should not be empty")
    @Size(min = 2, max = 30, message = "Min = 2, Max = 30")
    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "We want to know your birth date")
    private Date birthdate;

    @NotNull
    @Size(min = 6,max = 12, message = "Min = 6, Max = 12")
    private String passport;

    @NotNull
    @Size(min = 6,max = 40, message = "Min = 6, Max = 40")
    private String address;

    @Email(message = "Please, enter valid e-mail: ...@gmail.com")
    @Size(min = 6,max = 30, message = "Min = 6, Max = 30")
    private String email;

    @NotEmpty(message = "Should not be empty")
    @Size(min = 4,max = 20, message = "Min = 4, Max = 20")
    private String login;

    @NotEmpty(message = "Should not be empty")
    @Size(min = 4,max = 20, message = "Min = 4, Max = 20")
    private String password;

    private Role role;

    private Status status;
}
