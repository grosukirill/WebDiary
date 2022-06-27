package com.ru.questiondiary.web.entity;

public enum Social {
    FACEBOOK("Facebook"),
    INSTAGRAM("Instagram"),
    VK("VK"),
    TELEGRAM("Telegram"),
    TWITTER("Twitter");

    private final String social;

    Social(String social) {
        this.social = social;
    }

    public String getSocial() {
        return social;
    }
}
