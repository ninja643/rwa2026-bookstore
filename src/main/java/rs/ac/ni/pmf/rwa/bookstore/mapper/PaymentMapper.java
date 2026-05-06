package rs.ac.ni.pmf.rwa.bookstore.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentDto;
import rs.ac.ni.pmf.rwa.bookstore.model.dto.PaymentRequestDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PaymentEntity;

@Component
public class PaymentMapper
{
	public PaymentDto toDto(final PaymentEntity entity)
	{
		return PaymentDto.builder()
		                 .id(entity.getId())
		                 .orderId(entity.getOrder().getId())
		                 .paymentMethod(entity.getPaymentMethod())
		                 .status(entity.getStatus())
		                 .paymentDate(entity.getPaymentDate())
		                 .amount(entity.getAmount())
		                 .build();
	}

	public PaymentEntity toEntity(final PaymentRequestDto dto)
	{
		return PaymentEntity.builder()
		                    .paymentMethod(dto.getPaymentMethod())
		                    .amount(dto.getAmount())
		                    .build();
		// order i status se postavljaju u servisu
	}
}
