package redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() throws Exception {

        // set and retrive
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
        final Student retrievedStudent = studentRepository.findById(student.getId()).get();
        assertEquals(student.getId(), retrievedStudent.getId());

        // update
        retrievedStudent.setName("Richard Watson");
        studentRepository.save(student);

        // delete
        studentRepository.deleteById(student.getId());

        // find all students
        Student engStudent = new Student(
                "Eng2015001", "John Doe", Student.Gender.MALE, 1);
        Student medStudent = new Student(
                "Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
        studentRepository.save(engStudent);
        studentRepository.save(medStudent);

        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        System.out.println(students.size());
    }


}
