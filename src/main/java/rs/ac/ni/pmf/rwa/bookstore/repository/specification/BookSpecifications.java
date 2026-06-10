package rs.ac.ni.pmf.rwa.bookstore.repository.specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.SetJoin;
import org.springframework.data.jpa.domain.Specification;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AuthorEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.AuthorEntity_;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.BookEntity_;

import java.util.ArrayList;
import java.util.List;

public class BookSpecifications
{
	public static Specification<BookEntity> titleContainsSpecification(final String title)
	{
		return (root, query, criteriaBuilder) -> {
			if (title == null || title.isBlank())
			{
				return null;
			}

			query.orderBy(criteriaBuilder.asc(root.get(BookEntity_.title)));

			return criteriaBuilder.like(
					criteriaBuilder.lower(root.get(BookEntity_.title)),
					"%" + title.toLowerCase() + "%"
			);
		};
	}

	public static Specification<BookEntity> search(
			final String title,
			final String author)
	{
		return (root, query, criteriaBuilder) -> {

			final List<Predicate> predicates = new ArrayList<>();

			if (title != null && !title.isBlank())
			{
				predicates.add(criteriaBuilder.like(
						criteriaBuilder.lower(root.get(BookEntity_.title)),
						"%" + title.toLowerCase() + "%"
				));
			}

			if (author != null && !author.isBlank())
			{
				final SetJoin<BookEntity, AuthorEntity> authorJoin =
						root.join(BookEntity_.authors, JoinType.INNER);

				predicates.add(
						criteriaBuilder.equal(authorJoin.get(AuthorEntity_.firstName), author));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
