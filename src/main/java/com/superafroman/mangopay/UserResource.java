package com.superafroman.mangopay;

import com.superafroman.mangopay.domain.User;

public class UserResource {

    private static final String CREATE_PATH = "/users";

    private static final String FETCH_PATH = "/users/%s";

    private UserResource() {
    }

    public static User fetch(Long id) {
        Request r = new Request(User.class, "GET", String.format(FETCH_PATH, id));
        return r.send();
    }

    public static User create(User user) {
        Request r = new Request(User.class, "POST", CREATE_PATH);
        r.setEntity(user);
        return r.send();
    }
}
