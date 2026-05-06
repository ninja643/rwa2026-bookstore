package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponValidateRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.service.CouponService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController
{
	private final CouponService _couponService;

	@GetMapping
	public List<CouponDto> getAllCoupons()
	{
		return _couponService.getAllCoupons();
	}

	@GetMapping("/{id}")
	public CouponDto getCouponById(@PathVariable final Long id)
	{
		return _couponService.getCouponById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CouponDto createCoupon(@RequestBody @Valid final CouponRequestDto dto)
	{
		return _couponService.createCoupon(dto);
	}

	@PutMapping("/{id}")
	public CouponDto updateCoupon(@PathVariable final Long id,
	                              @RequestBody @Valid final CouponRequestDto dto)
	{
		return _couponService.updateCoupon(id, dto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCoupon(@PathVariable final Long id)
	{
		_couponService.deleteCoupon(id);
	}

	@PostMapping("/validate")
	public CouponDto validateCoupon(@RequestBody @Valid final CouponValidateRequestDto dto)
	{
		return _couponService.validateCoupon(dto);
	}
}
