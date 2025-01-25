package blog.app.controller;

import blog.app.constent.Constants;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.PostDetailsDto;
import blog.app.model.dto.PostDto;
import blog.app.model.dto.UpdatePostDto;
import blog.app.model.response.PostResponseDto;
import blog.app.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/post/")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public CustomResponseEntity<PostDto> createPost(@ModelAttribute PostDto postDto, @RequestParam MultipartFile file) {
        return postService.createPost(postDto, file);
    }


    @GetMapping
    public CustomResponseEntity<PostResponseDto> fetchAllPosts(
            @RequestParam(name = "pageNumber" , defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = Constants.PAGE_SIZE , required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir) {
        return postService.fetchAllPosts(pageNumber,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<PostDetailsDto> fetchPostDetails(@PathVariable Long id) {
        return postService.fetchPostDetails(id);
    }

    @DeleteMapping("/{postId}")
    public CustomResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @PutMapping("/{postId}")
    public CustomResponseEntity<UpdatePostDto> updatePost(@ModelAttribute UpdatePostDto updatePostDto, @PathVariable Long postId,@RequestParam MultipartFile file) {
        return postService.updatePost(updatePostDto, postId,file);
    }

    @GetMapping("category/{categoryId}")
    public CustomResponseEntity<?> fetchPostByCategory(@PathVariable Long categoryId) {
        return postService.fetchPostByCategory(categoryId);
    }

    @GetMapping("user/{userId}")
    public CustomResponseEntity<?> fetchPostByUser(@PathVariable Long userId) {
        return postService.fetchPostByUser(userId);
    }

    @GetMapping("search/keyword")
    public CustomResponseEntity<List<PostDetailsDto>> searchPostsByKeyword(@RequestParam("keyword") String keyword) {
        return postService.searchPostsByKeyword(keyword);
    }

    @GetMapping("search/title-and-content")
    public CustomResponseEntity<List<PostDetailsDto>> searchPostsByTitleAndContent(@RequestParam("keyword") String keyword) {
        return postService.searchPostsByTitleAndContent(keyword);
    }

    @GetMapping("search/single")
    public CustomResponseEntity<PostDetailsDto> searchSinglePostByKeyword(@RequestParam("keyword") String keyword) {
        return postService.searchSinglePostByKeyword(keyword);
    }

}
