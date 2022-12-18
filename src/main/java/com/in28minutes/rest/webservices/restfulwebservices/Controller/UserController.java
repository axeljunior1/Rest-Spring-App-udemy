package com.in28minutes.rest.webservices.restfulwebservices.Controller;

import com.in28minutes.rest.webservices.restfulwebservices.Beans.User;
import com.in28minutes.rest.webservices.restfulwebservices.User.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;


    @GetMapping("/users")
    Iterable<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping("/hellointer")
    String gethelloInter(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

    }



    @GetMapping("/users/{id}")
    Optional<User> getUserById(@PathVariable("id") Long id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("id: "+ id);
        }
        return user;
    }

    @PostMapping("/users")
    ResponseEntity<User> SaveUser(@Valid @RequestBody User user){
         User saveUser = userRepository.save(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    void DeleteById(@PathVariable("id") Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }
}
