package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AddressEntity;

@Component
public class AddressMapper
{
	public AddressDto toDto(final AddressEntity entity)
	{
		return AddressDto.builder()
		                 .id(entity.getId())
		                 .street(entity.getStreet())
		                 .city(entity.getCity())
		                 .postalCode(entity.getPostalCode())
		                 .country(entity.getCountry())
		                 .build();
	}

	public AddressEntity toEntity(final AddressRequestDto dto)
	{
		return AddressEntity.builder()
		                    .street(dto.getStreet())
		                    .city(dto.getCity())
		                    .postalCode(dto.getPostalCode())
		                    .country(dto.getCountry())
		                    .build();
		// user se postavlja u servisu
	}
}
