package de.hsmannheim.student.controller;

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

import de.hsmannheim.student.domain.Student;
import de.hsmannheim.student.persistence.StudentRepository;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins="*")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Student student) throws URISyntaxException {
		this.studentRepository.save(student);
		URI location = new URI("/student/" + student.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public Iterable<Student> getAll() throws URISyntaxException {
		return studentRepository.findAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student student) {
		if (this.studentRepository.exists(id)) {
			student.setId(id);
			this.studentRepository.save(student);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
