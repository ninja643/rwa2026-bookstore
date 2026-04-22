package rs.ac.ni.pmf.rwa.bookstore;

import rs.ac.ni.pmf.rwa.bookstore.model.User;

public class TestData
{
	public static class USERS {
		public static User ADMIN = User.builder()
				.id(1L)
				.username("admin")
				.firstName("admin")
				.lastName("admin")
				.build();

		public static User USER = User.builder()
				.id(2L)
				.username("user")
				.firstName("user")
				.lastName("user")
				.build();
	}
}
