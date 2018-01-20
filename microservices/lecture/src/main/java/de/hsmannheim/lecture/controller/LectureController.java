package de.hsmannheim.lecture.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.lecture.domain.Lecture;
import de.hsmannheim.lecture.persistence.LectureRepository;

@RestController
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	private LectureRepository lectureRepository;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Lecture lecture) throws URISyntaxException {
		this.lectureRepository.save(lecture);
		URI location = new URI("/lecture/" + lecture.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public Iterable<Lecture> getAll() throws URISyntaxException {
		return lectureRepository.findAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Lecture lecture) {
		if (this.lectureRepository.exists(id)) {
			lecture.setId(id);
			this.lectureRepository.save(lecture);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
