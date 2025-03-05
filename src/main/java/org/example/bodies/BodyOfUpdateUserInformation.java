package org.example.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyOfUpdateUserInformation {
    private String email;
    private String name;
}

