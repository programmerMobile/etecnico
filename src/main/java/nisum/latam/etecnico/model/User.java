package nisum.latam.etecnico.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;



    @OneToMany(cascade = CascadeType.ALL) // Cambia CascadeType.ALL a CascadeType.PERSIST
    @JoinColumn(name = "users.id")
    private List<Phone> phones;
    // Constructor with fields (except id, created, modified, lastLogin)
    public User(String name, String email, String password, String token, boolean isActive, List<Phone> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
        this.isActive = isActive;
        this.phones = phones;
    }

        // Constructor, getters y setters

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", lastLogin=" + lastLogin +
                ", token='" + token + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
