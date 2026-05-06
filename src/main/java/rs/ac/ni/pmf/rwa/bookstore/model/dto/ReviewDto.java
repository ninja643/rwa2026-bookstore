package rs.ac.ni.pmf.rwa.bookstore.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ReviewDto
{
	Long id;
	Long userId;
	String username;
	Integer rating;
	String comment;
	LocalDateTime reviewDate;
}
