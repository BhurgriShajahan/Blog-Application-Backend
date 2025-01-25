package blog.app.repository;

import blog.app.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    String findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}
