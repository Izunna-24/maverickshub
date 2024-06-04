package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
