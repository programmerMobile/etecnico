package nisum.latam.etecnico.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import nisum.latam.etecnico.exception.EmailAlreadyExistsException;
import nisum.latam.etecnico.exception.ValidationException;
import nisum.latam.etecnico.model.Phone;
import nisum.latam.etecnico.model.User;
import nisum.latam.etecnico.repository.PhoneRepository;
import nisum.latam.etecnico.repository.UserRepository;
import nisum.latam.etecnico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private Environment environment;



    @Override
    public User registerUser(User userRequest) {

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (!isValidEmail(userRequest.getEmail())) {
            throw new ValidationException("Correo inválido");
        }

        if (!isValidPassword(userRequest.getPassword())) {
            throw new ValidationException("Contraseña inválida");
        }

        // Validar y procesar la contraseña, crear token, etc.

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        String token = generateJwtToken(user);
        user.setToken(token);
        user.setActive(true);

        List<Phone> phones = new ArrayList<>();
        for (Phone phoneRequest : userRequest.getPhones()) {
            Phone phone = new Phone();
            phone.setNumber(phoneRequest.getNumber());
            phone.setCityCode(phoneRequest.getCityCode()); // Corregido aquí
            phone.setCountryCode(phoneRequest.getCountryCode()); // Corregido aquí
            phone.setUser(user);
            phones.add(phone);
        }

        user.setPhones(phones);
        userRepository.save(user);

        return user;
    }
    private boolean isValidEmail(String email) {
        // Validación de formato de correo utilizando expresiones regulares
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // Validación de formato de contraseña (por ejemplo, al menos 8 caracteres, letras y números)
        return password != null && password.length() >= 8 && password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }



    private String generateJwtToken(User user) {
        long expirationTimeMillis = Long.parseLong(environment.getProperty("jwt.expiration-time-ms"));
        byte[] secretKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded(); // Generar una clave segura

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, secretKeyBytes) // Usar la clave segura generada
                .compact();
    }
}