package users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static users.UserArray.*;


@RestController
public class UserArrayController {
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public ArrayList<User> UsersGet(@RequestParam(value="ID", required=false) Integer ID) {

        if (ID==null) return getUsers();
        else return getUser(ID);
    }

    @RequestMapping(value="/user", method=RequestMethod.POST)
    public void UserPost(String name, String surname, int age) {
        User user=new User(name, surname, age);
        createUser(user);
    }

    @RequestMapping(value="/user", method=RequestMethod.DELETE)
    public void UserDelete(long ID) {
        deleteUser(ID);
    }

    @RequestMapping(value="/user", method=RequestMethod.PUT)
    public void UserUpdate(long ID, String name, String surname, int age) {
        updateUser(ID, name, surname, age);
    }
}
