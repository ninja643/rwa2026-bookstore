package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.exception.InvalidOperationException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.mapper.CouponMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponValidateRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CouponEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.CouponRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService
{
	private final CouponRepository _couponRepository;
	private final CouponMapper _couponMapper;

	public List<CouponDto> getAllCoupons()
	{
		return _couponRepository.findAll().stream()
		                        .map(_couponMapper::toDto)
		                        .toList();
	}

	public CouponDto getCouponById(final Long id)
	{
		return _couponRepository.findById(id)
		                        .map(_couponMapper::toDto)
		                        .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id));
	}

	public CouponDto createCoupon(final CouponRequestDto dto)
	{
		final CouponEntity saved = _couponRepository.save(_couponMapper.toEntity(dto));
		return _couponMapper.toDto(saved);
	}

	public CouponDto updateCoupon(final Long id, final CouponRequestDto dto)
	{
		final CouponEntity existing = _couponRepository.findById(id)
		                                               .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id));

		existing.setCode(dto.getCode());
		existing.setDiscountValue(dto.getDiscountValue());
		existing.setDiscountType(dto.getDiscountType());
		existing.setValidFrom(dto.getValidFrom());
		existing.setValidTo(dto.getValidTo());
		existing.setUsageLimit(dto.getUsageLimit());

		return _couponMapper.toDto(_couponRepository.save(existing));
	}

	public void deleteCoupon(final Long id)
	{
		if (!_couponRepository.existsById(id))
		{
			throw new ResourceNotFoundException("Coupon not found with id: " + id);
		}

		_couponRepository.deleteById(id);
	}

	public CouponDto validateCoupon(final CouponValidateRequestDto dto)
	{
		final CouponEntity coupon = _couponRepository.findByCode(dto.getCode())
		                                             .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with code: " + dto.getCode()));

		assertCouponUsable(coupon);

		return _couponMapper.toDto(coupon);
	}

	public CouponEntity findAndAssertUsable(final String code)
	{
		final CouponEntity coupon = _couponRepository.findByCode(code)
		                                             .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with code: " + code));

		assertCouponUsable(coupon);

		return coupon;
	}

	private void assertCouponUsable(final CouponEntity coupon)
	{
		if (!coupon.getIsActive())
		{
			throw new InvalidOperationException("Coupon '" + coupon.getCode() + "' is not active");
		}

		final LocalDateTime now = LocalDateTime.now();

		if (coupon.getValidFrom() != null && now.isBefore(coupon.getValidFrom()))
		{
			throw new InvalidOperationException("Coupon '" + coupon.getCode() + "' is not yet valid");
		}

		if (coupon.getValidTo() != null && now.isAfter(coupon.getValidTo()))
		{
			throw new InvalidOperationException("Coupon '" + coupon.getCode() + "' has expired");
		}

		if (coupon.getUsageLimit() != null && coupon.getUsageCount() >= coupon.getUsageLimit())
		{
			throw new InvalidOperationException("Coupon '" + coupon.getCode() + "' has reached its usage limit");
		}
	}
}
