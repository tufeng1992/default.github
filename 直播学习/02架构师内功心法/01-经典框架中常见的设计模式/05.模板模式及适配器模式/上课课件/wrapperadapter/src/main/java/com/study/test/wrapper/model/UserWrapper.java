package com.study.test.wrapper.model;

public class UserWrapper {

    private User user;

    private String channel;

    public UserWrapper(User user, String channel) {
        this.user = user;
        this.channel = channel;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
