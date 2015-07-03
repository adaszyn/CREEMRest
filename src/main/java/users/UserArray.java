package users;

import java.util.ArrayList;
import java.util.Arrays;

public class UserArray {
    private static User test=new User("a","b",1);
    private static User test2=new User("emiel","regis",415);
    private static ArrayList<User> users=new ArrayList<>(Arrays.asList(test, test2));

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getUser(long ID) {
        ArrayList<User> tmp=new ArrayList<>();
        for (User user:users) {
            if (user.getID()==ID) {
                tmp.add(user);
            }
        }
        return tmp;
    }

    public static void createUser(User user) {
        users.add(user);
    }

    public static void deleteUser(long ID) {
        for (User user:users) {
            if (user.getID()==ID) {
                users.remove(user);
            }
        }
    }

    public static void updateUser(long ID, String name, String surname, int age) {
        for (User user:users) {
            if (user.getID()==ID) {
                user.setName(name);
                user.setSurname(surname);
                user.setAge(age);
            }
        }
    }
}
