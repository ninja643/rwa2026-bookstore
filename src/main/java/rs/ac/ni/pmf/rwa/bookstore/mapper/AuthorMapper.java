package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AuthorSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AuthorEntity;

@Component
public class AuthorMapper
{
	public AuthorDto toDto(final AuthorEntity entity)
	{
		return AuthorDto.builder()
		                .id(entity.getId())
		                .firstName(entity.getFirstName())
		                .lastName(entity.getLastName())
		                .biography(entity.getBiography())
		                .build();
	}

	public AuthorSummaryDto toSummaryDto(final AuthorEntity entity)
	{
		return AuthorSummaryDto.builder()
		                       .id(entity.getId())
		                       .firstName(entity.getFirstName())
		                       .lastName(entity.getLastName())
		                       .build();
	}

	public AuthorEntity toEntity(final AuthorRequestDto dto)
	{
		return AuthorEntity.builder()
		                   .firstName(dto.getFirstName())
		                   .lastName(dto.getLastName())
		                   .biography(dto.getBiography())
		                   .build();
	}
}
