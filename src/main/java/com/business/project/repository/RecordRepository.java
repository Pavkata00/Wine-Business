package com.business.project.repository;

import com.business.project.model.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    Optional<RecordEntity> findByName(String name);

    @Query("SELECT R FROM RecordEntity R " +
            "ORDER BY R.amount DESC ")
    List<RecordEntity> findMostBoughtWineRecords();
}
