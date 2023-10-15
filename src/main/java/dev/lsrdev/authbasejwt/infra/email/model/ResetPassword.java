package dev.lsrdev.authbasejwt.infra.email.model;

public class ResetPassword {
    private String link;
    private String userName;

    public ResetPassword(String link, String userName) {
        this.link = link;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getLink() {
        return link;
    }

}
