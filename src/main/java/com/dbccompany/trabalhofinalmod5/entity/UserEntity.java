package com.dbccompany.trabalhofinalmod5.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    String username;
    Integer age;
    String password;
    String email;
    boolean isactive;
}
