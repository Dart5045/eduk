package com.eduk.admission.service.dataaccess.student.repository;

import com.eduk.admission.service.dataaccess.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentEntity, UUID> {


}
