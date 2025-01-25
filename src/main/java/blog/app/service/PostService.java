package blog.app.service;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.PostDetailsDto;
import blog.app.model.dto.PostDto;
import blog.app.model.dto.UpdatePostDto;
import blog.app.model.entities.Post;
import blog.app.model.response.PostResponseDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    CustomResponseEntity<PostDto> createPost(PostDto postDto, MultipartFile file);
    CustomResponseEntity<PostResponseDto> fetchAllPosts(int pageNumber, int pageSize,String sortBy, String sortDir);
    CustomResponseEntity<PostDetailsDto> fetchPostDetails(Long postId);
    CustomResponseEntity<?> deletePost(Long id);
    CustomResponseEntity<UpdatePostDto> updatePost(UpdatePostDto updatePostDto, Long postId,MultipartFile file);
    CustomResponseEntity<?> fetchPostByCategory(Long categoryId);
    CustomResponseEntity<?> fetchPostByUser(Long userId);

    CustomResponseEntity<List<PostDetailsDto>> searchPostsByKeyword(String keyword);
    CustomResponseEntity<List<PostDetailsDto>> searchPostsByTitleAndContent(String keyword);
    CustomResponseEntity<PostDetailsDto> searchSinglePostByKeyword(String keyword);


}
