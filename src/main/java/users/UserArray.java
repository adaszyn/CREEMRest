package users;

import java.util.ArrayList;
import java.util.Arrays;

public class UserArray {
    private static User test=new User("a","b",1);
    private static User test2=new User("emiel","regis",415);
    private static ArrayList<User> users=new ArrayList<>(Arrays.asList(test, test2));

    public static ArrayList<User> getUsers() {return users;}

    public static ArrayList<User> getUser(String name, String surname) {
        ArrayList<User> tmp=new ArrayList<>();
        for (User user:users) {
            if (user.getName()==name && user.getSurname()==surname) {
                tmp.add(user);
            }
        }
        return tmp;
    }

    public static void createUser(User user) {
        users.add(user);
    }

}
