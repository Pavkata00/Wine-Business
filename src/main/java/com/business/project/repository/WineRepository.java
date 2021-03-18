package com.business.project.repository;

import com.business.project.model.entity.WineEntity;
import com.business.project.model.entity.enums.TypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<WineEntity, Long> {

    List<WineEntity> findAllByType(TypeEnum typeEnum);

    Optional<WineEntity> findByName(String name);
}
