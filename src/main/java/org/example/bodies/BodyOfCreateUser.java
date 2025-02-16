package org.example.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyOfCreateUser {
    private String email;
    private String password;
    private String name;
}