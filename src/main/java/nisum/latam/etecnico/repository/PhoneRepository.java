package nisum.latam.etecnico.repository;
import nisum.latam.etecnico.model.Phone;
import nisum.latam.etecnico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}