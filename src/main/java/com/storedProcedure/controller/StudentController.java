package com.storedProcedure.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.storedProcedure.dao.StudentDao;
import com.storedProcedure.model.Student;
import com.storedProcedure.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;;

	@GetMapping("/")
	public String AllStudents(Student student, HttpServletRequest request, Model model) {

		String Type = "AllStudents";
		System.out.println("Controller called");
		List<Student> students = studentService.AllStudents(student, Type, student.getId(), student.getName(),
				student.getCity(), student.getSalary());
		model.addAttribute("getAllStudents", students);
       
		return "userData";

	}

	@RequestMapping("/insert")
	public String insertUser(Student student) {

		return "addUser";

	}


	

	@PostMapping("/insertProcess")

	public RedirectView getStudent(Student student, HttpServletRequest request) {

		String Type = "insert";
		System.out.println("insert controller called");
		studentService.insertUser(student, Type, student.getId(), student.getName(), student.getCity(),
				student.getSalary());
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");

		return redirectView;
	}

	@GetMapping("/getStudent/{id}")
	public String getStudentById(@PathVariable("id") long id, Model model) {

		String Type = "getById";
		System.out.println("called GetByID controller");

		Optional<Student> retriveStudent = studentService.getStudentById(Type, id);
		model.addAttribute("SpecificStudentt", retriveStudent.orElse(null));

		return "specificStudent";

	}

	@GetMapping("/editStudent/{id}")
	public String editStudentById(@PathVariable("id") long id, Model model) {

		String Type = "getById";
		System.out.println("called editStudent controller");

		Optional<Student> retriveStudent = studentService.editStudentById(Type, id);
		model.addAttribute("editStudent", retriveStudent.orElse(null));

		return "updateUser";
	}

	@GetMapping("/update")
	public RedirectView updateStudentDetails(Model model, Student student,HttpServletRequest request) {

		String Type = "update";
		System.out.println("called UpdateStudent controller");
		Student retriveStudent = studentService.updatedStudent(student,Type);
		model.addAttribute("SpecificStudentt", retriveStudent);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");

		return redirectView;
		

	}
	
	
	@GetMapping("/delete/{id}")
	
	public RedirectView delete(@PathVariable("id") long id,HttpServletRequest request) {
		System.out.println("deleted controlled");
		String Type="delete";
		 studentService.deleteStudent(Type,id);
		
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl(request.getContextPath() + "/");
			return redirectView;
			
	}

}
