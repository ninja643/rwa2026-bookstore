package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookImageEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private BookEntity book;

	@Column(nullable = false, length = 500)
	private String url;

	@Column(name = "is_primary")
	@Builder.Default
	private Boolean isPrimary = false;
}
