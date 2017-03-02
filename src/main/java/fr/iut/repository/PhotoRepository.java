package fr.iut.repository;

import fr.iut.domain.Photo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
public interface PhotoRepository extends JpaRepository<Photo,Long> {

}
