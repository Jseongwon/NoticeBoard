package com.noticeboard.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {
    public static class Request {
        @Data
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Login {
            private String name;
            private String emailAddress;
            private String salt;

            @Builder
            public Login(String name, String emailAddress, String salt) {
                this.name = name;
                this.emailAddress = emailAddress;
                this.salt = salt;
            }
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {
        private Long id;
        private String name;
        private String emailAddress;
        private String password;
        private String salt;

        @Builder
        public Summary(Long id, String name, String emailAddress, String password, String salt) {
            this.id = id;
            this.name = name;
            this.emailAddress = emailAddress;
            this.password = password;
            this.salt = salt;
        }
    }
}
