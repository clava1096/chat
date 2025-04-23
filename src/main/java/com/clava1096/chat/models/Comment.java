package com.clava1096.chat.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "fk_comment_author_id"),
            nullable = false
    )
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            foreignKey = @ForeignKey(name = "fk_comment_post_id"),
            nullable = false
    )
    private Post post;
}
