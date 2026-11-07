package com.MarketConnect.GaadiDealer.repositories;

import com.MarketConnect.GaadiDealer.entities.TestDrive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestdriveRespository extends JpaRepository<TestDrive,Long> {

    @Query(value = "SELECT COUNT(*) FROM test_drive td WHERE td.is_done=:status",nativeQuery = true)
    Long findAllTestDrivesByStatus(@Param("status") boolean status);

    @Query(value = "SELECT * FROM test_drive td WHERE td.user_id=:userId",nativeQuery = true)
    Page<TestDrive> findAllTestDrivesByUserId(@Param("userId") Long userId, Pageable pageable);
}
