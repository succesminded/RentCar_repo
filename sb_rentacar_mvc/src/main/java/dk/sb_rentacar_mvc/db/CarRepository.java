package dk.sb_rentacar_mvc.db;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dk.sb_rentacar_mvc.model.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer>{

	@Query("SELECT * FROM cars WHERE active = 1")
	List<Car> getActiveCars();
	
	@Query("SELECT active FROM cars WHERE id = :carId")
	Integer getActiveByCarId(@Param("carId") Integer carId);
	
}
