package com.gajanan.Job.Posting.Application.model.dto;

import com.gajanan.Job.Posting.Application.model.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
