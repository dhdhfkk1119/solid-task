package com.puzzlix.solid_task.domain.user.dto;

import com.puzzlix.solid_task.domain.user.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class SignUp{

        @NotEmpty
        @Size(min = 2, max = 20)
        private String name;

        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

        private Role role;
    }

    @Data
    public static class Login{
        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;

    }
}
