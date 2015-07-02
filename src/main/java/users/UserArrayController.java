package users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static users.UserArray.createUser;
import static users.UserArray.getUser;
import static users.UserArray.getUsers;


@RestController
public class UserArrayController {
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public ArrayList<User> UsersGet(@RequestParam(value="name", defaultValue="") String name,
                           @RequestParam(value="surname", defaultValue="") String surname) {

        if (name =="" && surname=="") return getUsers();
        else return getUser(name, surname);
    }

    @RequestMapping(value="/user", method=RequestMethod.POST)
    public void UserPost(String name, String surname, int age) {
        User user=new User(name, surname, age);
        createUser(user);
    }

    @RequestMapping(value="/user", method=RequestMethod.DELETE)
    public String UserDelete(@ModelAttribute User user, Model model) {
        return "result";
    }

    @RequestMapping(value="/user", method=RequestMethod.PUT)
    public String UserUpdate(@ModelAttribute User user, Model model) {
        return "result";
    }
}
