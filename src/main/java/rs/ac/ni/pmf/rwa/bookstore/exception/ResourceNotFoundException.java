package rs.ac.ni.pmf.rwa.bookstore.exception;

public class ResourceNotFoundException extends RuntimeException
{
	public ResourceNotFoundException(final String message)
	{
		super(message);
	}
}
