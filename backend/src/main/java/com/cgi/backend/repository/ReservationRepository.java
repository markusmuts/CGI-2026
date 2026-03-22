package com.cgi.backend.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgi.backend.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReservationDate(LocalDate reservationDate);

    @Query(
        """
        select distinct reservation.table.id
        from Reservation reservation
        where reservation.startTime < :endTime
          and reservation.endTime > :startTime
        """
    )
    List<Long> findOccupiedTableIds(
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    @Query(
        """
        select count(reservation) > 0
        from Reservation reservation
        where reservation.table.id = :tableId
          and reservation.startTime < :endTime
          and reservation.endTime > :startTime
        """
    )
    boolean existsOverlappingReservation(
        @Param("tableId") Long tableId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
}