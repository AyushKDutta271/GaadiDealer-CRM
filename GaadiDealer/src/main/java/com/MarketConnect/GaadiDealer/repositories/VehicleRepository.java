package com.MarketConnect.GaadiDealer.repositories;

import com.MarketConnect.GaadiDealer.dtos.VehicleDto;
import com.MarketConnect.GaadiDealer.entities.Status;
import com.MarketConnect.GaadiDealer.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    @Query(value = "SELECT COUNT(*) FROM vehicle v WHERE v.status=:status",nativeQuery = true)
    Long findAllVehicleByStatus(@Param("status") String status);

    @Query(value = "SELECT SUM(v.car_price) FROM vehicle v GROUP BY v.status HAVING v.status=:status ",nativeQuery = true)
    Long findTotalEarnedMoney(@Param("status") String status);

    @Query(value = "SELECT * FROM vehicle v WHERE v.user_id=:userId",nativeQuery = true)
    Page<Vehicle> findAllCarsByUserId(@Param("userId") Long userId, Pageable pageable);

}
