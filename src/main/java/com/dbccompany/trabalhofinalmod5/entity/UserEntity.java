package com.dbccompany.trabalhofinalmod5.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String id;
    private String username;
    private Integer age;
    private String password;
    private String email;
    private boolean isactive;
}
