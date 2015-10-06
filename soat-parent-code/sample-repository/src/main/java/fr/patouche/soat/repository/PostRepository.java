package fr.patouche.soat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import fr.patouche.soat.entity.Post;

/**
 * The post repository.
 *
 * @author patouche - 06/10/15
 */
public interface PostRepository extends JpaRepository<Post, Long>, QueryDslPredicateExecutor<Post> {

}
