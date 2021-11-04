package com.epam.jwd.service.config;

public interface ValidatorConfig {

    Integer MAX_USERNAME_LENGTH = 32;
    Integer MIN_USERNAME_LENGTH = 3;
    String USERNAME_SPECIAL_SYMBOLS = "[^$&+,:;=?@#|'<>.^*()%!-\\\\\\/]";
}
