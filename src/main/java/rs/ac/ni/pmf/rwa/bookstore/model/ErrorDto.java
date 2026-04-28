package rs.ac.ni.pmf.rwa.bookstore.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class ErrorDto
{
	String message;
	OffsetDateTime timestamp;
}
