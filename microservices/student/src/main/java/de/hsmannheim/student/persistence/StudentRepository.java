package de.hsmannheim.student.persistence;

import org.springframework.data.repository.CrudRepository;

import de.hsmannheim.student.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
