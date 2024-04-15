package com.api.blog.service.impl;

import com.api.blog.dao.mapper.CommentMapper;
import com.api.blog.dao.pojo.Comment;
import com.api.blog.service.CommentsService;
import com.api.blog.service.SysUserService;
import com.api.blog.vo.CommentVo;
import com.api.blog.vo.Result;
import com.api.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService  sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1.根据文章查询
         * 2.根据作者id，查询作者信息
         * 3.判断如果level = 1要去查询有没有子评论，如果有根据评论id进行查询（parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = commentVo.getLevel();
        if (1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentsId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to user 给谁评论
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo userVoById = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentsId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return  copyList(commentMapper.selectList(queryWrapper));
    }
}
