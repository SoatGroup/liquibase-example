package fr.patouche.soat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import fr.patouche.soat.entity.Comment;
import fr.patouche.soat.entity.QComment;

/**
 * The comment repository.
 *
 * @author patouche - 06/10/15
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, QueryDslPredicateExecutor<Comment> {

    OrderSpecifier<Long> ORDER = QComment.comment.id.desc();

    List<Comment> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    final class Predicates {

        public static Predicate byPostId(final Long postId) {
            return QComment.comment.post.id.eq(postId);
        }

    }
}
