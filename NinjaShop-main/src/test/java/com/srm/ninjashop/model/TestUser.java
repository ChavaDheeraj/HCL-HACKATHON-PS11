package com.srm.ninjashop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUser {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String telephone;
    private final String password;

    public TestUser(String firstName, String lastName, String email, String telephone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }

    public static TestUser createUnique() {
        String stamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        return new TestUser(
                "Deepika",
                "tester",
                "test.tester." + stamp + "@mail.com",
                "9876543210",
                "Test@12345");
    }

    public static TestUser createUniqueFrom(TestUser source) {
        String stamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String uniqueEmail = source.getEmail();
        if (uniqueEmail.contains("@")) {
            String[] emailParts = uniqueEmail.split("@", 2);
            uniqueEmail = emailParts[0] + "." + stamp + "@" + emailParts[1];
        } else {
            uniqueEmail = uniqueEmail + "." + stamp + "@mailinator.com";
        }

        return new TestUser(
                source.getFirstName(),
                source.getLastName(),
                uniqueEmail,
                source.getTelephone(),
                source.getPassword());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPassword() {
        return password;
    }
}
