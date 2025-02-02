package blog.app.service.impl;

import blog.app.mapper.CommentMapper;
import blog.app.mapper.UpdateCommentMapper;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CommentDto;
import blog.app.model.dto.UpdateCommentDto;
import blog.app.model.entities.Comment;
import blog.app.model.entities.Post;
import blog.app.model.entities.User;
import blog.app.repository.CommentRepository;
import blog.app.repository.PostRepository;
import blog.app.repository.UserRepository;
import blog.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UpdateCommentMapper updateCommentMapper;

    @Override
    public CustomResponseEntity<CommentDto> createComment(CommentDto commentDto,Long postId) {
        try {

            if (commentDto == null) {
                logger.warn("Comment DTO is null during comment creation.");
                return CustomResponseEntity.error("Comment DTO cannot be null!");
            }

            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isEmpty()) {
                logger.error("Post not found with ID: {}", commentDto.getPostId());
                return CustomResponseEntity.error("Post is not present!");
            }

            Optional<User> optionalUser = userRepository.findById(commentDto.getUserId());
            if (optionalUser.isEmpty()) {
                logger.error("User not found with ID: {}", commentDto.getUserId());
                return CustomResponseEntity.error("User is not present!");
            }

            Post post = optionalPost.get();
            User user = optionalUser.get();

            Comment comment = commentMapper.dtoToEntity(commentDto);
            comment.setPost(post);
            comment.setUserId(user.getId());

            comment = commentRepository.save(comment);

            commentDto = commentMapper.entityToDtoWithPostId(comment);
            logger.info("Comment created successfully with ID: {}", comment.getId());

            // Return success response
            return new CustomResponseEntity<>(commentDto, "Comment created successfully.");
        } catch (Exception exception) {
            logger.error("An error occurred during comment creation: {}", exception.getMessage(), exception);
            return CustomResponseEntity.error("An error occurred during comment creation.");
        }
    }

    @Override
    public CustomResponseEntity<UpdateCommentDto> updateComment(UpdateCommentDto commentDto, Long commentId) {
        try {
            if (commentDto == null || commentId == null) {
                logger.warn("Invalid input: commentDto or commentId is null during update.");
                return CustomResponseEntity.error("Invalid input: Comment DTO or ID cannot be null.");
            }

            Optional<Comment> existingComment = commentRepository.findById(commentId);

            if (existingComment.isEmpty()) {
                logger.warn("Comment with ID {} not found for update.", commentId);
                return CustomResponseEntity.error("Comment not found.");
            }

            existingComment.get().setContent(commentDto.getContent());
            Comment updatedComment = commentRepository.save(existingComment.get());
            UpdateCommentDto updatedDto = updateCommentMapper.entityToDto(updatedComment);

            logger.info("Comment updated successfully with ID: {}", commentId);
            return new CustomResponseEntity<>(updatedDto, "Comment updated successfully.");
        } catch (Exception exception) {
            logger.error("An error occurred during comment update: {}", exception.getMessage(), exception);
            return CustomResponseEntity.error("An error occurred during comment update.");
        }
    }

    @Override
    public CustomResponseEntity<?> deleteComment(Long commentId) {
        try {
            if (commentId == null) {
                logger.warn("Invalid input: commentId is null during delete.");
                return CustomResponseEntity.error("Comment ID cannot be null.");
            }

            Optional<Comment> existComment=commentRepository.findById(commentId);

            if (existComment.isEmpty()) {
                logger.warn("Comment with ID {} not found for deletion.", commentId);
                return CustomResponseEntity.error("Comment not found.");
            }
            Comment comment = existComment.get();
            commentRepository.deleteById(comment.getId());

            logger.info("Comment deleted successfully with ID: {}", commentId);
            return new CustomResponseEntity<>(comment,"Comment deleted successfully.");
        } catch (Exception exception) {
            logger.error("An error occurred during comment deletion: {}", exception.getMessage(), exception);
            return CustomResponseEntity.error("An error occurred during comment deletion.");
        }
    }
}
