package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", length = 100)
	private String firstName;

	@Column(name = "last_name", length = 100)
	private String lastName;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(nullable = false, unique = true, length = 100)
	private String username;

	@Column(length = 50)
	private String phone;

	@Column(name = "registration_date", nullable = false)
	private LocalDateTime registrationDate;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	@Builder.Default
	private Set<RoleEntity> roles = new HashSet<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private CartEntity cart;
}
