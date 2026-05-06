package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.AddressMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AddressEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.AddressRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService
{
	private final AddressRepository _addressRepository;
	private final UserRepository _userRepository;
	private final AddressMapper _addressMapper;

	public List<AddressDto> getAddressesByUserId(final Long userId)
	{
		if (!_userRepository.existsById(userId))
		{
			throw new ResourceNotFoundException("User not found with id: " + userId);
		}

		return _addressRepository.findByUserId(userId).stream()
		                         .map(_addressMapper::toDto)
		                         .toList();
	}

	public AddressDto getAddressById(final Long userId, final Long addressId)
	{
		final AddressEntity address = _addressRepository.findById(addressId)
		                                                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

		if (!address.getUser().getId().equals(userId))
		{
			throw new ResourceNotFoundException("Address not found with id: " + addressId + " for user: " + userId);
		}

		return _addressMapper.toDto(address);
	}

	public AddressDto createAddress(final Long userId, final AddressRequestDto dto)
	{
		final UserEntity user = _userRepository.findById(userId)
		                                       .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		final AddressEntity entity = _addressMapper.toEntity(dto);
		entity.setUser(user);

		return _addressMapper.toDto(_addressRepository.save(entity));
	}

	public AddressDto updateAddress(final Long userId, final Long addressId, final AddressRequestDto dto)
	{
		final AddressEntity existing = _addressRepository.findById(addressId)
		                                                 .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

		if (!existing.getUser().getId().equals(userId))
		{
			throw new ResourceNotFoundException("Address not found with id: " + addressId + " for user: " + userId);
		}

		existing.setStreet(dto.getStreet());
		existing.setCity(dto.getCity());
		existing.setPostalCode(dto.getPostalCode());
		existing.setCountry(dto.getCountry());

		return _addressMapper.toDto(_addressRepository.save(existing));
	}

	public void deleteAddress(final Long userId, final Long addressId)
	{
		final AddressEntity existing = _addressRepository.findById(addressId)
		                                                 .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

		if (!existing.getUser().getId().equals(userId))
		{
			throw new ResourceNotFoundException("Address not found with id: " + addressId + " for user: " + userId);
		}

		_addressRepository.deleteById(addressId);
	}
}
