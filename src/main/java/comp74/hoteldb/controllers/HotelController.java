package comp74.hoteldb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comp74.hoteldb.model.entities.Room;
import comp74.hoteldb.model.entities.Guest;
import comp74.hoteldb.model.entities.Reservation;
import comp74.hoteldb.model.repos.GuestRepo;
import comp74.hoteldb.model.repos.RoomRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class HotelController {

    RoomRepo roomRepo;
    GuestRepo guestRepo;

    @Autowired
    public HotelController(RoomRepo roomRepo, GuestRepo guestRepo) {
        super();
        this.roomRepo = roomRepo;
        this.guestRepo = guestRepo;
    }

    /**
     * http://localhost:8080/api/rooms?pageNumber=N
     * 
     * Receives a page number and sorting criteria in URI from index.html form - http://localhost:8080/index.html
     *  
     * @param pageNumber
     * @return
     */
    @GetMapping("/rooms")
    public Page<Room> getRooms(
        @RequestParam(defaultValue = "0") Integer pageNumber,
        @RequestParam(defaultValue = "roomId") String sortBy
    ) {
        //Need to set paging information
        PageRequest pageInfo;

        pageInfo = PageRequest.of(pageNumber, 10, Sort.by(sortBy));

        //Want to return a page of data, not a list
        return (Page<Room>) roomRepo.findAll(pageInfo);
    }


    /**
     * localhost:8080/api/rooms/N 
     * 
     * @param id
     * @return
     */
    @GetMapping("/rooms/{id}")
    public Room findRoomById(@PathVariable Long id)
    {
        Optional<Room> opt =  roomRepo.findById(id);
        if (!opt.isEmpty())
            return opt.get();
        return null;
    }

/**
 * * localhost:8080/api/rooms/8/res
 * 
 * @param id
 * @return
 */
    @RequestMapping ("/rooms/{id}/res")
    public List<Reservation>
    findReservationByRoomId(@PathVariable Long id)
    {
        Room room = roomRepo.findById(id).get();
        return room.getReservations();
    }

    /**
     * http://localhost:8080/api/guests?pageNumber=N
     * 
     * 
     * @param page
     * @param sort
     * @return
     */
    @GetMapping("/guests")
    public Page<Guest> getGuests(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "id") String sort 
        )
    {
        PageRequest pageInfo;

        pageInfo = PageRequest.of(page, 12, Sort.by(sort));

        //Want to return a page of data, not a list
        return (Page<Guest>) guestRepo.findAll(pageInfo);

    }
}
