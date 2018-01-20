package de.hsmannheim.room.persistence;

import org.springframework.data.repository.CrudRepository;

import de.hsmannheim.lecture.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

}
