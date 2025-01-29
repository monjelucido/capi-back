package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.ExperienceDTO;
import com.capitravel.Capitravel.model.Experience;
import com.capitravel.Capitravel.model.UserExperienceReview;

import java.time.LocalDateTime;
import java.util.List;

public interface ExperienceService {

    List<Experience> getAllExperiences();

    Experience getExperienceById(Long id);

    List<Experience> getExperiencesByCategories(List<Long> categoryIds);

    List<String> getCountriesFromExperiences();

    Experience createExperience(ExperienceDTO experience);

    Experience updateExperience(Long id, ExperienceDTO updatedExperience);

    void deleteExperience(Long id);

    List<Experience> searchExperiences(String keywords, String country, LocalDateTime startDate, LocalDateTime endDate);

    List<Experience> getFavoritesExperiences(List<Long> experienceIdList);

    UserExperienceReview reviewExperience(Long id, String email, double newRating, String review);

    double alreadyRated(Long id, String email);

    List<UserExperienceReview> getAllExperienceReviews(Long experienceId);
}
