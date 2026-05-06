package rs.ac.ni.pmf.rwa.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.ni.pmf.rwa.bookstore.model.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 50)
	private String code;

	@Column(name = "discount_value", precision = 10, scale = 2)
	private BigDecimal discountValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "discount_type", length = 20)
	private DiscountType discountType;

	@Column(name = "valid_from")
	private LocalDateTime validFrom;

	@Column(name = "valid_to")
	private LocalDateTime validTo;

	@Column(name = "usage_limit")
	private Integer usageLimit;

	@Column(name = "usage_count", nullable = false)
	@Builder.Default
	private Integer usageCount = 0;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private Boolean isActive = true;

	@ManyToMany(mappedBy = "coupons")
	@Builder.Default
	private Set<OrderEntity> orders = new HashSet<>();
}
