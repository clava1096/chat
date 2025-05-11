package com.clava1096.chat.controllers;

import com.clava1096.chat.models.Comment;
import com.clava1096.chat.models.Post;
import com.clava1096.chat.models.dto.PostDTO;
import com.clava1096.chat.models.graphql.PostInput;
import com.clava1096.chat.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "post-controller")
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @QueryMapping
    public List<Post> searchPosts(@Argument String keyword) {
        return postService.searchPosts(keyword);
    }

    @Operation(summary = "Создание поста")
    @ApiResponse(responseCode = "201", description = "В случае успешного выполнения возвращает экземпляр класса PostDTO",
            content = @Content(schema = @Schema(implementation = PostDTO.class)))
    @MutationMapping
    public Post createPost(
            @Argument("input") PostInput postInput
            //@Argument String linkImage
    ) {
        return postService.createPost(postInput.getTitle(), postInput.getContent());
    }

    @MutationMapping
    public Post updatePost(
            @Argument UUID id,
            @Argument("input") PostInput postInput) {
        return postService.updatePost(id, postInput);
    }


    @QueryMapping
    public Post getPost(@Argument UUID id) {
        return postService.getPostById(id);
    }

    @QueryMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @MutationMapping
    public Boolean deletePost(@Argument UUID id) {
        return postService.deletePost(id);
    }

    @SchemaMapping(typeName = "Post", field = "comments")
    public List<Comment> getComments(Post post) {
        return post.getComments();
    }

}
