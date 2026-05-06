package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.CouponSummaryDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.CouponEntity;

@Component
public class CouponMapper
{
	public CouponDto toDto(final CouponEntity entity)
	{
		return CouponDto.builder()
		                .id(entity.getId())
		                .code(entity.getCode())
		                .discountValue(entity.getDiscountValue())
		                .discountType(entity.getDiscountType())
		                .validFrom(entity.getValidFrom())
		                .validTo(entity.getValidTo())
		                .usageLimit(entity.getUsageLimit())
		                .usageCount(entity.getUsageCount())
		                .isActive(entity.getIsActive())
		                .build();
	}

	public CouponSummaryDto toSummaryDto(final CouponEntity entity)
	{
		return CouponSummaryDto.builder()
		                       .code(entity.getCode())
		                       .discountValue(entity.getDiscountValue())
		                       .discountType(entity.getDiscountType())
		                       .build();
	}

	public CouponEntity toEntity(final CouponRequestDto dto)
	{
		return CouponEntity.builder()
		                   .code(dto.getCode())
		                   .discountValue(dto.getDiscountValue())
		                   .discountType(dto.getDiscountType())
		                   .validFrom(dto.getValidFrom())
		                   .validTo(dto.getValidTo())
		                   .usageLimit(dto.getUsageLimit())
		                   .build();
	}
}
