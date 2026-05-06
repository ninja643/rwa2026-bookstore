package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PublisherSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PublisherEntity;

@Component
public class PublisherMapper
{
	public PublisherDto toDto(final PublisherEntity entity)
	{
		return PublisherDto.builder()
		                   .id(entity.getId())
		                   .name(entity.getName())
		                   .city(entity.getCity())
		                   .country(entity.getCountry())
		                   .build();
	}

	public PublisherSummaryDto toSummaryDto(final PublisherEntity entity)
	{
		return PublisherSummaryDto.builder()
		                          .id(entity.getId())
		                          .name(entity.getName())
		                          .build();
	}

	public PublisherEntity toEntity(final PublisherRequestDto dto)
	{
		return PublisherEntity.builder()
		                      .name(dto.getName())
		                      .city(dto.getCity())
		                      .country(dto.getCountry())
		                      .build();
	}
}
