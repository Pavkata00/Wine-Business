package com.business.project.repository;

import com.business.project.model.entity.FactoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<FactoryEntity, Long> {

    Optional<FactoryEntity> findByName(String name);
}
