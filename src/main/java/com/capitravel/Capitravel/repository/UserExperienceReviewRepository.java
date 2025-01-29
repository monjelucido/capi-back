package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.UserExperienceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserExperienceReviewRepository extends JpaRepository<UserExperienceReview, Long> {
    boolean existsByEmailAndExperienceId(String email, Long experienceId);

    Optional<UserExperienceReview> findByEmailAndExperienceId(String email, Long experienceId);

    List<UserExperienceReview> findAllByExperienceId(Long experienceId);
}