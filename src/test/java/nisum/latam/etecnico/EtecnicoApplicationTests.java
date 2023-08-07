package nisum.latam.etecnico;

import nisum.latam.etecnico.exception.EmailAlreadyExistsException;
import nisum.latam.etecnico.exception.ValidationException;
import nisum.latam.etecnico.model.Phone;
import nisum.latam.etecnico.model.User;
import nisum.latam.etecnico.repository.UserRepository;
import nisum.latam.etecnico.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class EtecnicoApplicationTests {

	@Mock
	private Environment environment;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService; // Cambia a la implementación concreta

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegisterUser_Success() throws EmailAlreadyExistsException, ValidationException {
		// Simulate environment behavior
		when(environment.getProperty("jwt.expiration-time-ms")).thenReturn("3600000"); // 1 hour
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		// Create a fictitious UserRequest
		User userRequest = new User();
		userRequest.setName("Pepito Doe");
		userRequest.setEmail("Pepito@example.com");
		userRequest.setPassword("P@ssw0rd");

		// Create a fictitious Phone
		Phone phone = new Phone();
		phone.setNumber("1234567");
		phone.setCityCode("1");
		phone.setCountryCode("57");

		// Create a List of Phones
		List<Phone> phones = new ArrayList<>();
		phones.add(phone);

		// Set the list of phones in the user request
		userRequest.setPhones(phones);
		// Set other fields as needed

		// Call the method to be tested
		User registeredUser = userService.registerUser(userRequest);

		// Verify that the repository method was called to save the user
		verify(userRepository, times(1)).save(any(User.class));

		// Perform additional assertions based on your service logic
		assertNotNull(registeredUser);
		// Add more assertions as needed
	}

	@Test
	public void testRegisterUser_EmailAlreadyExists() {
		// Simulate environment behavior
		when(environment.getProperty("jwt.expiration-time-ms")).thenReturn("3600000"); // 1 hour
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

		// Create a fictitious UserRequest
		User userRequest = new User();
		userRequest.setName("John Doe");
		userRequest.setEmail("john@example.com");
		userRequest.setPassword("P@ssw0rd");
		// Set other fields as needed

		// Call the method to be tested and assert for EmailAlreadyExistsException
		assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(userRequest));

		// Verify that the repository method was not called to save the user
		verify(userRepository, never()).save(any(User.class));
	}

}