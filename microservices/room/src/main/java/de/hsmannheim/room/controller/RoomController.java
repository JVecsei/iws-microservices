package de.hsmannheim.room.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.room.domain.Room;
import de.hsmannheim.room.persistence.RoomRepository;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins="*")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Room room) throws URISyntaxException {
		this.roomRepository.save(room);
		URI location = new URI("/room/" + room.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public Iterable<Room> getAll() throws URISyntaxException {
		return roomRepository.findAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Room room) {
		if (this.roomRepository.exists(id)) {
			room.setId(id);
			this.roomRepository.save(room);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
