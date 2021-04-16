package netology.servlets.service;

import netology.servlets.exception.NotFoundException;
import netology.servlets.model.Post;
import org.springframework.stereotype.Service;
import netology.servlets.repository.PostRepository;

import java.util.List;


public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(int id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(int id) {
        repository.removeById(id);
    }
}
