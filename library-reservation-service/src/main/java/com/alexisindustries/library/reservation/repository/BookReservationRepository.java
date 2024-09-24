package com.alexisindustries.library.reservation.repository;

import com.alexisindustries.library.reservation.model.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
    int countBookReservationByBookId(Long bookId);
    List<BookReservation> findByBorrowedTimeIsNullOrReturnTimeBefore(LocalDateTime now);
}