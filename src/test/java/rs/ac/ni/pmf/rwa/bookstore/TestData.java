package rs.ac.ni.pmf.rwa.bookstore;

import rs.ac.ni.pmf.rwa.bookstore.model.UserDto;

public class TestData
{
	public static class USERS {
		public static UserDto ADMIN = UserDto.builder()
		                                     .id(1L)
		                                     .username("admin")
		                                     .firstName("admin")
		                                     .lastName("admin")
		                                     .build();

		public static UserDto USER = UserDto.builder()
		                                    .id(2L)
		                                    .username("user")
		                                    .firstName("user")
		                                    .lastName("user")
		                                    .build();
	}
}
