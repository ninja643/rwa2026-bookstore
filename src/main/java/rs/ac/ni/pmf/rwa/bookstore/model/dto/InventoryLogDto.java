package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class InventoryLogDto
{
	Long id;
	BookSummaryDto book;
	Integer changeAmount;
	String reason;
	LocalDateTime createdAt;
}
