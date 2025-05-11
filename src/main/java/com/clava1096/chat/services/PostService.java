package com.clava1096.chat.services;

import com.clava1096.chat.exceptions.PostNotFoundException;
import com.clava1096.chat.mappers.PostMapper;
import com.clava1096.chat.models.Post;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.graphql.PostInput;
import com.clava1096.chat.models.repositories.PostRepository;
import com.clava1096.chat.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }
    @Transactional(readOnly = true)
    public List<Post> searchPosts(String keyword) {
        return null;
        //return postRepository.findByTitleContainingOrContentContaining(keyword);
    }

    @Transactional
    public Post createPost(String title, String content) {
        User author = userService.getCurrentUser();

        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();
        post = postRepository.saveAndFlush(post);

        if (post.getId() == null) {
            throw new IllegalStateException("Post ID not generated after save");
        }
        log.info("Saved post: {}", post);
        return post;
    }


    public Post updatePost(UUID id, PostInput postInput) {
        return postRepository.updatePost(id, postInput);
    }

    public boolean deletePost(UUID id) {
        postRepository.deleteById(id);
        return !postRepository.existsById(id);
    }
}
