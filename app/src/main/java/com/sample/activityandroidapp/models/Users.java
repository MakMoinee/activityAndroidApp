package com.sample.activityandroidapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Users {
    private int userID;
    private String userName;
    private String password;

    int userType;

    public Users(UserBuilder userBuilder) {
        this.userID = userBuilder.userID;
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.userType = userBuilder.userType;
    }

    public static class UserBuilder {
        int userID;
        String userName;
        String password;

        int userType;

        public UserBuilder() {

        }

        public UserBuilder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setUserType(int userType) {
            this.userType = userType;
            return this;
        }

        public Users build() {
            return new Users(this);
        }
    }
}
