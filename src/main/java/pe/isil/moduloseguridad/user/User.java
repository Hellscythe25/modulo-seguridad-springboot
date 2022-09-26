package pe.isil.moduloseguridad.user;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user", uniqueConstraints = {@UniqueConstraint(name = "mail_unique", columnNames = "email"),
                                                @UniqueConstraint(name = "document_unique", columnNames = "document")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "username", nullable = true)
    private String name;
    @Column(name = "document", nullable = false)
    private String document;

    //Get and Set
}
