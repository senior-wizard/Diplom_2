package org.example.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyOfLoginUser {
    private String email;
    private String password;
}