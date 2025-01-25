package blog.app.model.response;

import blog.app.model.dto.PostDetailsDto;

import java.util.List;

public class PostResponseDto {

    private List<PostDetailsDto> content;
    private int pageNumber;
    private int pageSize;
    private Long pageElement;
    private Long totalPages;
    private Boolean lastPage;

    //Getters & Setters

    public List<PostDetailsDto> getContent() {
        return content;
    }

    public void setContent(List<PostDetailsDto> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageElement() {
        return pageElement;
    }

    public void setPageElement(Long pageElement) {
        this.pageElement = pageElement;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }
}
