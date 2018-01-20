package de.hsmannheim.lecture.persistence;

import org.springframework.data.repository.CrudRepository;

import de.hsmannheim.lecture.domain.Lecture;

public interface LectureRepository extends CrudRepository<Lecture, Long> {

}
