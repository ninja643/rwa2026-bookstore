package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.UserDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;

@Component
public class UserMapper
{
	public UserDto toDto(final UserEntity userEntity)
	{
		return UserDto.builder()
		              .id(userEntity.getId())
		              .username(userEntity.getUsername())
		              .firstName(userEntity.getFirstName())
		              .lastName(userEntity.getLastName())
		              .email(userEntity.getEmail())
		              .phoneNumber(userEntity.getPhone())
		              .build();
	}

//	public UserEntity toEntity(final UserDto userDto)
//	{
//
//	}
}
