package org.example.api;

import org.example.api.Model.Item;
import org.example.api.Model.Post;
import org.example.api.Model.User;
import org.example.api.Repository.ItemRepository;
import org.example.api.Repository.PostRepository;
import org.example.api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String index() {
        return "24ss3";
    }

    @GetMapping("/xd")
    public String xd() {
        return "s";
    }


    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll(); // Ensure PostRepository is set up correctly
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Ensure PostRepository is set up correctly
    }
    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll(); // Ensure PsostRepository is set up correctly
    }
}