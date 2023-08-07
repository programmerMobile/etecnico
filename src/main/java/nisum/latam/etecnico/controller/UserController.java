package nisum.latam.etecnico.controller;

import nisum.latam.etecnico.exception.EmailAlreadyExistsException;
import nisum.latam.etecnico.exception.ValidationException;
import nisum.latam.etecnico.model.User;
import nisum.latam.etecnico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userRequest) {
        try {
            User user = userService.registerUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body( "{\"message\": \"Usuario registrado correctamente\""+",\"message\":  \"" + user + "\"}");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"El correo ya registrado\"}");
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
