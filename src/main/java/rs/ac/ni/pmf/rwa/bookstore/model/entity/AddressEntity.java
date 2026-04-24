package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255)
	private String street;

	@Column(length = 100)
	private String city;

	@Column(name = "postal_code", length = 20)
	private String postalCode;

	@Column(length = 100)
	private String country;
}
