package com.in28minutes.rest.webservices.restfulwebservices.Controller;

import com.in28minutes.rest.webservices.restfulwebservices.Beans.Post;
import com.in28minutes.rest.webservices.restfulwebservices.Beans.User;
import com.in28minutes.rest.webservices.restfulwebservices.User.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class Controller {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final MessageSource messageSource;

    public Controller(UserRepository userRepository, PostRepository postRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.messageSource = messageSource;
    }


    // User Mapping
    @GetMapping("/users")
    Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/hello-inter")
    String getHelloInter() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

    }

    @GetMapping("/users/{id}")
    EntityModel<Optional<User>> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);
        EntityModel<Optional<User>> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAll());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    ResponseEntity<User> SaveUser(@Valid @RequestBody User user) {
        User saveUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    void DeleteById(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    // Post Mapping

    @GetMapping("/users/{id}/posts")
    Iterable<Post> getAllPosts(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return user.getPosts();
    }

    @GetMapping("/posts/{id}")
    Optional<Post> getPostById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @GetMapping("/posts")
    Iterable<Post> getPostById() {
        return postRepository.findAll();
    }


    @PostMapping("/users/{id}/posts")
    Post SavePost(@PathVariable Long id, @RequestBody Post post) {
        post.setUser(userRepository.findById(id).get());
        return postRepository.save(post);
    }


}
