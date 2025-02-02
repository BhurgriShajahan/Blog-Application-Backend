package blog.app.controller;

import blog.app.endpoints.PostControllerEndpoints;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.PostDetailsDto;
import blog.app.model.dto.PostDto;
import blog.app.model.dto.UpdatePostDto;
import blog.app.model.dto.response.PostResponseDto;
import blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController implements PostControllerEndpoints {

    private final PostService postService;

    @Override
    public CustomResponseEntity<PostDto> createPost(PostDto postDto,MultipartFile file) {
        return postService.createPost(postDto, file);
    }

    @Override
    public CustomResponseEntity<PostResponseDto> fetchAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return postService.fetchAllPosts(pageNumber,pageSize,sortBy,sortDir);
    }
    @Override
    public CustomResponseEntity<PostDetailsDto> fetchPostDetails(Long id) {
        return postService.fetchPostDetails(id);
    }

    @Override
    public CustomResponseEntity<?> deletePost(Long postId) {
        return postService.deletePost(postId);
    }

    @Override
    public CustomResponseEntity<UpdatePostDto> updatePost(UpdatePostDto updatePostDto,Long postId,MultipartFile file) {
        return postService.updatePost(updatePostDto, postId,file);
    }

    @Override
    public CustomResponseEntity<?> fetchPostByCategory(Long categoryId) {
        return postService.fetchPostByCategory(categoryId);
    }

    @Override
    public CustomResponseEntity<?> fetchPostByUser(Long userId) {
        return postService.fetchPostByUser(userId);
    }

    @Override
    public CustomResponseEntity<List<PostDetailsDto>> searchPostsByKeyword(String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    @Override
    public CustomResponseEntity<List<PostDetailsDto>> searchPostsByTitleAndContent(String keyword) {
        return postService.searchPostsByTitleAndContent(keyword);
    }

    @Override
    public CustomResponseEntity<PostDetailsDto> searchSinglePostByKeyword(String keyword) {
        return postService.searchSinglePostByKeyword(keyword);
    }

}
