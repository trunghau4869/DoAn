package com.example.demo.repository.CommentRepo;

import com.example.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
