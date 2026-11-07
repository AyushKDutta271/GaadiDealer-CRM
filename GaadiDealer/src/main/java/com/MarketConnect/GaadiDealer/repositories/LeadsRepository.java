package com.MarketConnect.GaadiDealer.repositories;

import com.MarketConnect.GaadiDealer.entities.Leads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadsRepository extends JpaRepository<Leads,Long> {

    @Query(value = "SELECT COUNT(*) FROM leads WHERE is_verified=:status",nativeQuery = true)
    Long findAllLeads(@Param("status") boolean status);

    @Query(value = "SELECT * FROM leads WHERE test_drive_id=:testdriveId",nativeQuery = true)
    Optional<Leads> findLeadsByTestDriveId(@Param("testdriveId")Long testdriveId);

    @Query(value = "SELECT * FROM leads WHERE buy=:buyStatus",nativeQuery = true)
    List<Leads> findAllLeadsByBuy(@Param("buyStatus") Boolean buyStatus);
}
