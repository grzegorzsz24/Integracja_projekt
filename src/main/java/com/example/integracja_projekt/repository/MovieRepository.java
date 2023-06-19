package com.example.integracja_projekt.repository;

import com.example.integracja_projekt.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    @Query("UPDATE MovieEntity m SET m.title = 'The Dark Cat' WHERE m.title = 'The Dark Knight'")
    @Modifying
    void updateTtitle();
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<MovieEntity> findAll();
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    List<MovieEntity> findMoviesByGenresOrderByRatingDesc(@Param("genres") String genre);
}
