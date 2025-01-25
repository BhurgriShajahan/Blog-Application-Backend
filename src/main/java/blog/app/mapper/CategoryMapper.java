package blog.app.mapper;

import blog.app.model.dto.CategoryDto;
import blog.app.model.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category dtoToEntity(CategoryDto category);

    CategoryDto entityToDto(Category categoryDto);

}
