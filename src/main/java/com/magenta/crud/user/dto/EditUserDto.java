package com.magenta.crud.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class EditUserDto implements Serializable {

    @NotEmpty(message = "This field should not be empty")
    @Size(min = 2, max = 30, message = "Min - 2, Max - 30")
    private String firstname;

    @NotEmpty(message = "This field should not be empty")
    @Size(min = 2, max = 30, message = "Min - 2, Max - 30")
    private String lastname;

    @Size(min = 6, max = 12, message = "Enter correct number")
    private String passport;

    @NotEmpty(message = "Have you moved? Where?")
    private String address;

    @Email
    private String email;

    @NotEmpty(message = "Should not be empty")
    private String password;
}
