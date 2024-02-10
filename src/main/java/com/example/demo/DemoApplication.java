package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address(
					"Bangalore",
					"India",
					"560103");
			Student student = new Student(
					"Mohan",
					"Kumar",
					"mohan.kumar@gmail.com",
					Gender.MALE,
					address,
					List.of("Maths", "Science", "History"),
					BigDecimal.TEN,
					LocalDateTime.now()

			);
			String email = student.getEmail();

			// getStudentsUsingMongoTemplate(studentRepository, mongoTemplate, student,
			// email);

			studentRepository.findStudentByEmail(email)
					.ifPresentOrElse(s -> System.out.println(s + " already exists"),
							() -> {
								System.out.println("Inserting student " + student);
								studentRepository.insert(student);
							});
		};
	}
	/**
	 * private void getStudentsUsingMongoTemplate(StudentRepository
	 * studentRepository, MongoTemplate mongoTemplate,
	 * Student student,
	 * String email) {
	 * Query query = new Query();
	 * query.addCriteria(Criteria.where("email").is(email));
	 * 
	 * List<Student> students = mongoTemplate.find(query, Student.class);
	 * 
	 * if (students.size() > 1) {
	 * throw new IllegalStateException("found many students with email " + email);
	 * }
	 * 
	 * if (students.isEmpty()) {
	 * System.out.println("Inserting student " + student);
	 * studentRepository.insert(student);
	 * } else {
	 * System.out.println(student + " already exists");
	 * }
	 * }
	 **/
}
