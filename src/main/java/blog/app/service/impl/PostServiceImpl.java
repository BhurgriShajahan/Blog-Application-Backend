package blog.app.service.impl;

import blog.app.mapper.PostDetailsMapper;
import blog.app.mapper.PostMapper;
import blog.app.mapper.UpdatePostMapper;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.PostDetailsDto;
import blog.app.model.dto.PostDto;
import blog.app.model.dto.UpdatePostDto;
import blog.app.model.entities.Category;
import blog.app.model.entities.Post;
import blog.app.model.entities.User;
import blog.app.model.dto.response.PostResponseDto;
import blog.app.repository.CategoryRepository;
import blog.app.repository.PostRepository;
import blog.app.repository.UserRepository;
import blog.app.service.PostService;
import blog.app.util.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostDetailsMapper postDetailsMapper;
    private final UpdatePostMapper updatePostMapper;
    private final AuthenticatedUser authenticatedUser;

    @Override
    public CustomResponseEntity<PostDto> createPost(PostDto postDto, MultipartFile file) {
        try {

            Long authUserId = authenticatedUser.getAuthUserId();
            logger.info("Creating a new post with title: {}", postDto.getTitle());

            // Fetch Category and User
            Optional<Category> category = categoryRepository.findById(postDto.getCategoryId());
            if (category.isEmpty()) {
                return CustomResponseEntity.error("Category not found");
            }

            Optional<User> user = userRepository.findById(authUserId);

            if (user.isEmpty()) {
                return CustomResponseEntity.error("User not found!");
            }

            postDto.setUserId(user.get().getId());
            postDto.setCategoryId(category.get().getId());
            postDto.setCreateAt(new Date());

            Post post = postMapper.dtoToEntity(postDto);

            post.setUser(user.get());
            post.setCreateAt(new Date());

            // Save file to a directory
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String uploadDir = "uploads/";
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                post.setImagePath(fileName);
            }

            Post savedPost = postRepository.save(post);

            logger.info("Post created successfully with ID: {}", savedPost.getId());

            return new CustomResponseEntity<>(postMapper.entityToDto(savedPost), "Post created successfully");
        } catch (Exception e) {
            logger.error("Error occurred while creating the post: {}", e.getMessage(), e);
            return CustomResponseEntity.error("Failed to create post");
        }
    }


    @Override
    public CustomResponseEntity<PostResponseDto> fetchAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        try {
            if (pageNumber < 0 || pageSize <= 0) {
                logger.warn("Invalid pagination parameters: pageNumber={}, pageSize={}", pageNumber, pageSize);
                return  CustomResponseEntity.error( "Invalid pagination parameters");
            }

            Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortDir).ascending():Sort.by(sortDir).descending();

            // Determine sorting direction
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

            logger.info("Fetching posts with pagination - pageNumber: {}, pageSize: {}, sortBy: {}, sortDir: {}",
                    pageNumber, pageSize, sortBy, sortDir);

            Page<Post> postPage = postRepository.findAll(pageable);
            List<Post> posts = postPage.getContent();

            List<PostDetailsDto> postDtos = posts.stream()
                    .map(postDetailsMapper::entityToDto)
                    .toList();
            //This is for pagination
            PostResponseDto postResponseDto = new PostResponseDto();
            postResponseDto.setContent(postDtos);
            postResponseDto.setPageNumber(postPage.getNumber());
            postResponseDto.setPageSize(postPage.getSize());
            postResponseDto.setPageElement(postPage.getTotalElements());
            postResponseDto.setLastPage(postPage.isLast());

            logger.info("Fetched {} posts from page {}", postDtos.size(), postPage.getNumber());

            return new CustomResponseEntity<>(postResponseDto, "Posts fetched successfully");
        } catch (Exception e) {
            logger.error("Error occurred while fetching posts: {}", e.getMessage(), e);
            return CustomResponseEntity.error( "Failed to fetch posts");
        }
    }



    @Override
    public CustomResponseEntity<PostDetailsDto> fetchPostDetails(Long postId) {
        try {
            logger.info("Fetching details for post with ID: {}", postId);
            Optional<Post> opPost = postRepository.findById(postId);
            if (opPost.isEmpty()){
                return CustomResponseEntity.error("Posts is not found!");
            }
            Post post = opPost.get();
            PostDetailsDto postDetailsDto = postDetailsMapper.entityToDto(post);

            return new CustomResponseEntity<>(postDetailsDto, "Post fetched successfully");
        } catch (Exception e) {
            logger.error("Error occurred while fetching post details: {}", e.getMessage(), e);
            return new CustomResponseEntity<>("Failed to fetch post details");
        }
    }

    @Override
    public CustomResponseEntity<?> deletePost(Long id) {
        try {
            logger.info("Deleting post with ID: {}", id);
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()){
                return CustomResponseEntity.error("Post not found!");
            }
            postRepository.delete(post.get());

            logger.info("Post deleted successfully with ID: {}", id);
            return new CustomResponseEntity<>( "Post deleted successfully");

        } catch (Exception e) {
            logger.error("Error occurred while deleting post: {}", e.getMessage(), e);
            return CustomResponseEntity.error( "Failed to delete post");
        }
    }

    @Override
    public CustomResponseEntity<UpdatePostDto> updatePost(UpdatePostDto updatePostDto, Long postId, MultipartFile file) {
        try {
            logger.info("Updating post with ID: {}", postId);

            Optional<Post> existingPostOptional = postRepository.findById(postId);
            if (existingPostOptional.isEmpty()) {
                return CustomResponseEntity.error("Post not found!");
            }

            Post existingPost = existingPostOptional.get();

            existingPost.setTitle(updatePostDto.getTitle());
            existingPost.setContent(updatePostDto.getContent());

            if (file != null && !file.isEmpty()) {
                String filePath = saveFile(file);
                existingPost.setImagePath(filePath);
            }

            existingPost.setUpdateAt(new Date());

            Post updatedPost = postRepository.save(existingPost);

            UpdatePostDto updatedPostDto = updatePostMapper.entityToDto(updatedPost);

            logger.info("Post updated successfully with ID: {}", postId);
            return new CustomResponseEntity<>(updatedPostDto, "Post updated successfully");

        } catch (Exception e) {
            logger.error("Error occurred while updating post: {}", e.getMessage(), e);
            return CustomResponseEntity.error("Failed to update post");
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    @Override
    public CustomResponseEntity<?> fetchPostByCategory(Long categoryId) {
        try {
            logger.info("Fetching posts for category ID: {}", categoryId);
            List<Post> posts = postRepository.findByCategoryId(categoryId);
            if (posts.isEmpty()) {
                logger.warn("No posts found for category ID: {}", categoryId);
                return new CustomResponseEntity<>("No posts found for this category");
            }
            List<PostDetailsDto> postDtos = posts.stream().map(postDetailsMapper::entityToDto).toList();
            logger.info("Fetched {} posts for category ID: {}", postDtos.size(), categoryId);
            return new CustomResponseEntity<>(postDtos, "Posts fetched successfully");
        } catch (Exception e) {
            logger.error("Error occurred while fetching posts by category: {}", e.getMessage(), e);
            return CustomResponseEntity.error("Failed to fetch posts by category");
        }
    }

    @Override
    public CustomResponseEntity<?> fetchPostByUser(Long userId) {
        try {
            logger.info("Fetching posts for user ID: {}", userId);
            List<Post> posts = postRepository.findByUserId(userId);
            if (posts.isEmpty()) {
                logger.warn("No posts found for user ID: {}", userId);
                return CustomResponseEntity.error( "No posts found for this user");
            }

            List<PostDetailsDto> postDtos = posts.stream().map(postDetailsMapper::entityToDto).toList();

            logger.info("Fetched {} posts for user ID: {}", postDtos.size(), userId);
            return new CustomResponseEntity<>(postDtos, "Posts fetched successfully");
        } catch (Exception e) {
            logger.error("Error occurred while fetching posts by user: {}", e.getMessage(), e);
            return CustomResponseEntity.error( "Failed to fetch posts by user");
        }
    }


        @Override
        public CustomResponseEntity<List<PostDetailsDto>> searchPostsByKeyword(String keyword) {
            try {
                logger.info("Searching posts by keyword: {}", keyword);

                List<Post> posts = postRepository.searchPostsByKeyword(keyword);

                if (posts.isEmpty()) {
                    logger.warn("No posts found for keyword: {}", keyword);
                    return CustomResponseEntity.error("No posts found for the provided keyword");
                }

                List<PostDetailsDto> postDetailsDtos = posts.stream()
                        .map(postDetailsMapper::entityToDto)
                        .toList();

                logger.info("Found {} posts for keyword: {}", postDetailsDtos.size(), keyword);
                return new CustomResponseEntity<>(postDetailsDtos, "Posts fetched successfully");
            } catch (Exception e) {
                logger.error("Error occurred while searching posts by keyword: {}", e.getMessage(), e);
                return CustomResponseEntity.error("Failed to search posts by keyword");
            }
        }

        @Override
        public CustomResponseEntity<List<PostDetailsDto>> searchPostsByTitleAndContent(String keyword) {
            try {
                logger.info("Searching posts by title and content with keyword: {}", keyword);

                List<Post> posts = postRepository.searchPostsByTitleAndContent(keyword);

                if (posts.isEmpty()) {
                    logger.warn("No posts found for title and content matching keyword: {}", keyword);
                    return CustomResponseEntity.error("No posts found for the provided keyword");
                }

                List<PostDetailsDto> postDetailsDtos = posts.stream()
                        .map(postDetailsMapper::entityToDto)
                        .toList();

                logger.info("Found {} posts for title and content with keyword: {}", postDetailsDtos.size(), keyword);
                return new CustomResponseEntity<>(postDetailsDtos, "Posts fetched successfully");
            } catch (Exception e) {
                logger.error("Error occurred while searching posts by title and content: {}", e.getMessage(), e);
                return CustomResponseEntity.error("Failed to search posts by title and content");
            }
        }

    @Override
    public CustomResponseEntity<PostDetailsDto> searchSinglePostByKeyword(String keyword) {
        try {
            logger.info("Searching for a single post with keyword: {}", keyword);

            Optional<Post> optionalPost = postRepository.searchSinglePostByKeyword(keyword);

            if (optionalPost.isEmpty()) {
                logger.warn("No post found for keyword: {}", keyword);
                return CustomResponseEntity.error("No post found for the provided keyword");
            }

            PostDetailsDto postDetailsDto = postDetailsMapper.entityToDto(optionalPost.get());

            logger.info("Post found for keyword: {}", keyword);
            return new CustomResponseEntity<>(postDetailsDto, "Post fetched successfully");
        } catch (Exception e) {
            logger.error("Error occurred while searching for a single post by keyword: {}", e.getMessage(), e);
            return CustomResponseEntity.error("Failed to fetch post");
        }
    }

}

