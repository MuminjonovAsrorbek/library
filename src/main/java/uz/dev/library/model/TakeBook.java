package uz.dev.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 5/1/25 20:07
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "take_books")
public class TakeBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @NotBlank
    @Column(nullable = false, name = "last_name")
    private String lastName;

    @NotBlank
    @Column(nullable = false, name = "phone")
    private String phone;

    @NotBlank
    @Column(nullable = false, name = "passport")
    private String passport;

    @Column(nullable = false)
    private Date takeDate;

    @NotNull(message = "Return date can not be null!")
    @Future(message = "Return date must be in the future!")
    @Column(nullable = false)
    private Date returnDate;

    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Book> books;

}
