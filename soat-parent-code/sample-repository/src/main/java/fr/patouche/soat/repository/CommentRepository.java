package fr.patouche.soat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import fr.patouche.soat.entity.Comment;

/**
 * The comment repository.
 *
 * @author patouche - 06/10/15
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, QueryDslPredicateExecutor<Comment> {

}
