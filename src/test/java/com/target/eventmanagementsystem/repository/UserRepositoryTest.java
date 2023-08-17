package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.target.eventmanagementsystem.models.Gender.MALE;
import static com.target.eventmanagementsystem.models.Gender.FEMALE;
import static com.target.eventmanagementsystem.models.UserRoles.STUDENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByEmail() {
        User user = new User(null, "John", "Doe", LocalDate.of(1990, 1, 15),
                MALE, "john@example.com", "password", STUDENT);
        entityManager.persist(user);
        entityManager.flush();

        // Act
        List<User> foundUsers = userRepository.findByEmail(user.getEmail());

        // Assert
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.size()).isEqualTo(1);
        assertThat(foundUsers.get(0)).isEqualTo(user);
    }

    @Test
    public void testFindAll() {
        // Save any existing users to the database
        userRepository.save(new User(2L, "John", "Smith", LocalDate.of(1985, 6, 20), MALE, "jane@example.com", "password", STUDENT));

        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testFindById() {
        User user=new User(null, "Jane", "Smith", LocalDate.of(1985, 6, 20), FEMALE, "jane@example.com", "password", STUDENT);
        entityManager.persist(user);
        entityManager.flush();
        Long userId=user.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        assertTrue(userOptional.isPresent());
        assertEquals("Jane", userOptional.get().getFirstName());
    }

    @Test
    public void testSave() {
        User user = new User(null, "NewUser", "LastName", LocalDate.of(1990, 1, 1),
                MALE, "newuser@example.com", "password123", STUDENT);

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("NewUser", savedUser.getFirstName());
    }

    @Test
    public void testDeleteById() {
        userRepository.deleteById(1L);
        assertFalse(userRepository.existsById(1L));
    }

    @Test
    public void testExistsById() {
        User user = new User(3L, "NewUser", "LastName", LocalDate.of(1990, 1, 1),
                MALE, "newuser@example.com", "password123", STUDENT);

        userRepository.save(user);
        assertTrue(userRepository.existsById(3L));
        assertFalse(userRepository.existsById(100L));
    }
}
