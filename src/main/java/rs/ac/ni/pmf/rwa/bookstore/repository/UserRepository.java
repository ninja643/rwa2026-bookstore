package rs.ac.ni.pmf.rwa.bookstore.repository;

import rs.ac.ni.pmf.rwa.bookstore.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository
{
	public static Map<Long, User> USERS = new HashMap<>();

	static
	{
		USERS.put(1L, User.builder()
		                  .id(1L)
		                  .username("admin")
		                  .firstName("admin")
		                  .lastName("admin")
		                  .build());

		USERS.put(2L, User.builder()
		                  .id(2L)
		                  .username("user")
		                  .firstName("user")
		                  .lastName("user")
		                  .build());
	}

	public static Long nextId()
	{
		return USERS.keySet()
		            .stream()
		            .max(Long::compareTo)
		            .map(id -> id + 1)
		            .orElse(1L);
	}
}
