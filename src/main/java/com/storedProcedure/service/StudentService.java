package com.storedProcedure.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storedProcedure.dao.StudentDao;
import com.storedProcedure.model.Student;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	@PersistenceContext

	private EntityManager em;

	public Student insertUser(Student student, String Type, Long id, String name, String city, int salary) {
		System.out.println("Service is called");
		StoredProcedureQuery query = (StoredProcedureQuery) em
				.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure").setParameter("Type", Type)
				.setParameter("id", id).setParameter("name", name).setParameter("city", city)
				.setParameter("salary", salary);

		query.execute();
		return studentDao.save(student);

	}

	public List<Student> AllStudents(Student student, String Type, Long id, String name, String city, Integer salary) {
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure");
			query.execute();

			List<Student> students = query.getResultList();
			return students;

		} catch (Exception ex) {

			System.out.println(ex.getMessage());
		}
		return studentDao.findAll();

	}

//	@Transactional
//	 public Optional<Student> getStudentById(Student student,String Type ,Long id,String name,String city ,Integer salary)
//	{
//		 StoredProcedureQuery query = em.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure")
//		           .setParameter("Type", Type)
//		   		   .setParameter("id", id)
//		           .setParameter("name", name)
//  		           .setParameter("city", city)
//  		           .setParameter("salary", salary);
//		 query.execute();
//		 
//			return studentDao.findById(id);
//	    }
//	
//	

	public Optional<Student> getStudentById(String type, Long id) {
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure")
					.setParameter("Type", type).setParameter("id", id);

			query.execute();
			List<Student> students = query.getResultList();

			if (!students.isEmpty()) {
				return Optional.of(students.get(0));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return studentDao.findById(id);

	}
	
	

	public Optional<Student> editStudentById(String type, Long id) {
		try {
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure")
					.setParameter("Type", type)
					.setParameter("id", id);

			query.execute();
			List<Student> students = query.getResultList();

			if (!students.isEmpty()) {
				return Optional.of(students.get(0));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return studentDao.findById(id);

	}
	
	
	@Transactional
	public Student updatedStudent(Student student, String Type) {
	  
	  System.out.println("Service is called"); 
	  StoredProcedureQuery query = em.createNamedStoredProcedureQuery("CrudCallProcedureFromProcedure")
	  .setParameter("Type", Type)
	  .setParameter("id", student.getId())
	  .setParameter("name", student.getName())
	  .setParameter("city", student.getCity())
	  .setParameter("salary", student.getSalary());
	  
	  query.execute();
	  
	  return studentDao.save(student);
	 }

	
	public void deleteStudent(String Type,long id) {

 	 studentDao.deleteById(id);
		
		
	}
	 
}
