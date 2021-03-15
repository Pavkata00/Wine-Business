package com.business.project.repository;

import com.business.project.model.entity.WineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends JpaRepository<WineEntity, Long> {
}
