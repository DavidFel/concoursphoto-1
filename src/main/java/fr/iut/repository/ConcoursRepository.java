package fr.iut.repository;

import fr.iut.domain.Concours;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Concours entity.
 */
@SuppressWarnings("unused")
public interface ConcoursRepository extends JpaRepository<Concours,Long> {

}
