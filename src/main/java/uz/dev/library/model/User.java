package uz.dev.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.dev.library.enums.Role;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 5/1/25 19:45
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username can not be blank!")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password can not be blank!")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Book> book;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

}
