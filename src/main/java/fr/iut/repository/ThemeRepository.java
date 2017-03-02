package fr.iut.repository;

import fr.iut.domain.Theme;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Theme entity.
 */
@SuppressWarnings("unused")
public interface ThemeRepository extends JpaRepository<Theme,Long> {

    @Query("select distinct theme from Theme theme left join fetch theme.concours")
    List<Theme> findAllWithEagerRelationships();

    @Query("select theme from Theme theme left join fetch theme.concours where theme.id =:id")
    Theme findOneWithEagerRelationships(@Param("id") Long id);

}
