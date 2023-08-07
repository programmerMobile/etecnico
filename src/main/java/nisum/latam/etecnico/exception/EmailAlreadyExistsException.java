package nisum.latam.etecnico.exception;


public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("El correo ya registrado");
    }
}