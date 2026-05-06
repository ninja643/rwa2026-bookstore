package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.ReviewRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.ReviewEntity;

@Component
public class ReviewMapper
{
	public ReviewDto toDto(final ReviewEntity entity)
	{
		return ReviewDto.builder()
		                .id(entity.getId())
		                .userId(entity.getUser().getId())
		                .username(entity.getUser().getUsername())
		                .rating(entity.getRating())
		                .comment(entity.getComment())
		                .reviewDate(entity.getReviewDate())
		                .build();
	}

	public ReviewEntity toEntity(final ReviewRequestDto dto)
	{
		return ReviewEntity.builder()
		                   .rating(dto.getRating())
		                   .comment(dto.getComment())
		                   .build();
		// user i book se postavljaju u servisu
	}
}
