package eai.formation.scurity.demo.controllers;

import eai.formation.scurity.demo.entities.UserDemo;
import eai.formation.scurity.demo.exceptions.UserNotFoundException;
import eai.formation.scurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public String findListUsers(Model model, Authentication authentication){
        model.addAttribute("userslist",userRepository.findAll());
        model.addAttribute("user",new UserDemo());
        //Collection<GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList());
        System.out.println(roles.toString());
        model.addAttribute("isAdmin",roles.contains("ADMIN"));
        return  "users";
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDemo> findUserbyId(@PathVariable String id ) throws UserNotFoundException {
        if(userRepository.findById(Long.valueOf(id)).isPresent()){
            return new ResponseEntity<>(userRepository.findById(Long.valueOf(id)).get(),HttpStatus.OK) ;
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
        }
    }
    @PostMapping("/users")
    public String saveUser(@ModelAttribute UserDemo userDemo){
        userDemo.setPassword(new BCryptPasswordEncoder().encode(userDemo.getPassword()));
        userRepository.save(userDemo);
        return "redirect:/users";
    }

    @PutMapping("/users")
    public ResponseEntity<UserDemo> updateuser(@RequestBody  UserDemo userDemo) throws UserNotFoundException {
        if(userRepository.findById(userDemo.getId()).isPresent()){
            return new ResponseEntity<>(userRepository.save(userDemo), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND) ;
        }
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDemo> deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK) ;
    }
}
