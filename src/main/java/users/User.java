package users;

public class User {
    private String name, surname;
    private int age;
    private long ID=0;

    private static long counter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String name, String surname, int age) {
        counter++;
        this.ID=counter;
        this.name=name;
        this.surname=surname;
        this.age=age;
    }

    public long getID() {
        return ID;
    }
}
