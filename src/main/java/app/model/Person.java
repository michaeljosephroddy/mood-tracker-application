package app.model;

abstract sealed class Person permits User {

    private String username;
    private String email;

    public Person() {
    }

    public Person(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getName() {
        return this.username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{username='" + username + "', email='" + email + "'}";
    }
}