package com.clava1096.chat.models.repositories;

import com.clava1096.chat.models.Post;
import com.clava1096.chat.models.User;
import com.clava1096.chat.models.graphql.PostInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findByTitle(String title);

    Post findByAuthor(User author);

    Post updatePost(UUID id, PostInput postInput);

    boolean deleteByTitle(String title);

//    @Query("SELECT p FROM Post p WHERE " +
//            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Post> findByTitleContainingOrContentContaining(
//            @Param("keyword") String keyword);
}
