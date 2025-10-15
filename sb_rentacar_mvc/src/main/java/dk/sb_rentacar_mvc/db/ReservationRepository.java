package dk.sb_rentacar_mvc.db;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dk.sb_rentacar_mvc.model.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer>{

	@Query("SELECT car_id FROM reservations WHERE (start_date <= :endDate) AND (end_date >= :startDate)")
	List<Integer> getCarIdReseverdInUserDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("SELECT car_id FROM reservations WHERE (start_date <= CURDATE()) AND (end_date >= CURDATE()) AND car_id = :carId")
	List<Integer> isCarInReservationPeriod(@Param("carId") Integer carId);
}
