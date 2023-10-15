package dev.lsrdev.authbasejwt.domain.permission.enums;

public enum Permissions {
    ADM("ADM", "Permission for admins."),
    COMMON("COMMON", "Permission for common users.");

    private String name;
    private String description;

    Permissions(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
