package com.example.demo.service.Comment;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Override
    public void create(Comment comment) {
        commentRepo.save(comment);
    }

}
