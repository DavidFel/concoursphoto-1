package fr.iut.repository;

import fr.iut.domain.UserPhotoComment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserPhotoComment entity.
 */
@SuppressWarnings("unused")
public interface UserPhotoCommentRepository extends JpaRepository<UserPhotoComment,Long> {

}
