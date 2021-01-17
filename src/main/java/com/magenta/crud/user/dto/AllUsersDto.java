package com.magenta.crud.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllUsersDto {

    private List<UserDto> listOfUsers;
}
