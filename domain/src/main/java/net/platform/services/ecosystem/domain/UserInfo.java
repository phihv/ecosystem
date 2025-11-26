package net.platform.services.ecosystem.domain;

import lombok.Getter;

@Getter
public class UserInfo {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private UserInfo(
            String username,
            String password,
            String firstName,
            String lastName
    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserInfo create(
            String username,
            String password,
            String firstName,
            String lastName
    ) {
        return new UserInfo(username, password, firstName, lastName);
    }
}
