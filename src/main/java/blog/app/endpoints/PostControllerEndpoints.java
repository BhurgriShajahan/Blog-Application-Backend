package blog.app.endpoints;

import blog.app.constent.Constants;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.PostDetailsDto;
import blog.app.model.dto.PostDto;
import blog.app.model.dto.UpdatePostDto;
import blog.app.model.response.PostResponseDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/v1/post/")
public interface PostControllerEndpoints {

    //Create post
    @PostMapping
    CustomResponseEntity<PostDto> createPost(@ModelAttribute PostDto postDto, @RequestParam MultipartFile file);

    //Fetch all posts
    @GetMapping
    CustomResponseEntity<PostResponseDto> fetchAllPosts(
            @RequestParam(name = "pageNumber" , defaultValue = Constants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = Constants.PAGE_SIZE , required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir);

    //Fetch post details
    @GetMapping("/{id}")
    CustomResponseEntity<PostDetailsDto> fetchPostDetails(@PathVariable Long id);

    //Delete post
    @DeleteMapping("/{postId}")
    CustomResponseEntity<?> deletePost(@PathVariable Long postId);

    //Update post
    @PutMapping("/{postId}")
    CustomResponseEntity<UpdatePostDto> updatePost(@ModelAttribute UpdatePostDto updatePostDto, @PathVariable Long postId, @RequestParam MultipartFile file);

    //Fetch post by category
    @GetMapping("category/{categoryId}")
    CustomResponseEntity<?> fetchPostByCategory(@PathVariable Long categoryId);

    //Fetch post by user
    @GetMapping("user/{userId}")
    CustomResponseEntity<?> fetchPostByUser(@PathVariable Long userId);

    //Search post
    @GetMapping("search/keyword")
    CustomResponseEntity<List<PostDetailsDto>> searchPostsByKeyword(@RequestParam("keyword") String keyword);

    //Search post with post title
    @GetMapping("search/title-and-content")
    CustomResponseEntity<List<PostDetailsDto>> searchPostsByTitleAndContent(@RequestParam("keyword") String keyword);

    //Search single post
    @GetMapping("search/single")
    CustomResponseEntity<PostDetailsDto> searchSinglePostByKeyword(@RequestParam("keyword") String keyword);

}
