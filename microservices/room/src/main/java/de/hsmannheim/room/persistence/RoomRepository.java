package de.hsmannheim.room.persistence;

import org.springframework.data.repository.CrudRepository;

import de.hsmannheim.room.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

}
