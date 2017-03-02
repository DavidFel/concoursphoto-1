package fr.iut.repository;

import fr.iut.domain.SiteUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SiteUser entity.
 */
@SuppressWarnings("unused")
public interface SiteUserRepository extends JpaRepository<SiteUser,Long> {

}
