package comp74.hoteldb.model.repos;

import org.springframework.data.repository.CrudRepository;

import comp74.hoteldb.model.entities.Guest;

public interface GuestRepo extends CrudRepository<Guest, Long >{
    
}
