package com.example.demo.service.Comment;

import com.example.demo.model.Comment;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void create(Comment comment);
}
