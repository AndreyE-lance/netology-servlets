package netology.servlets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import netology.servlets.controller.PostController;
import netology.servlets.repository.PostRepository;
import netology.servlets.service.PostService;

@Configuration
public class Config {

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }

}
