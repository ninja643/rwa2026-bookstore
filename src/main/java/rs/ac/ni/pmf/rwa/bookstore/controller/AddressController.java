package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.AddressRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController
{
	private final AddressService _addressService;

	@GetMapping
	public List<AddressDto> getAddressesByUserId(@PathVariable final Long userId)
	{
		return _addressService.getAddressesByUserId(userId);
	}

	@GetMapping("/{addressId}")
	public AddressDto getAddressById(@PathVariable final Long userId,
	                                 @PathVariable final Long addressId)
	{
		return _addressService.getAddressById(userId, addressId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AddressDto createAddress(@PathVariable final Long userId,
	                                @RequestBody @Valid final AddressRequestDto dto)
	{
		return _addressService.createAddress(userId, dto);
	}

	@PutMapping("/{addressId}")
	public AddressDto updateAddress(@PathVariable final Long userId,
	                                @PathVariable final Long addressId,
	                                @RequestBody @Valid final AddressRequestDto dto)
	{
		return _addressService.updateAddress(userId, addressId, dto);
	}

	@DeleteMapping("/{addressId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAddress(@PathVariable final Long userId,
	                          @PathVariable final Long addressId)
	{
		_addressService.deleteAddress(userId, addressId);
	}
}
