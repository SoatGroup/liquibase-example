package fr.patouche.soat.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.patouche.soat.LiquibaseHelper;
import fr.patouche.soat.entity.Comment;
import fr.patouche.soat.entity.Post;
import fr.patouche.soat.repository.config.SpringDataJpaConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CommentRepositoryTest.TestConfig.class, SpringDataJpaConfiguration.class })
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            final EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
            return new LiquibaseHelper(dataSource).update().getDataSource();
        }
    }

    @Before
    public void setUp() {
        this.commentRepository.deleteAll();
        this.postRepository.deleteAll();
    }

    @Test
    public void checkPredicates() throws Exception {

        // ARRANGE
        final Post post1 = this.postRepository.save(new Post("author", "post-1", "content-1"));
        final Post post2 = this.postRepository.save(new Post("author", "post-2", "content-2"));

        final Comment comment1 = this.commentRepository.save(new Comment(post1, "author", "content-1"));
        final Comment comment2 = this.commentRepository.save(new Comment(post1, "author", "content-2"));
        final Comment comment3 = this.commentRepository.save(new Comment(post1, "author", "content-3"));
        final Comment comment4 = this.commentRepository.save(new Comment(post2, "author", "content-4"));
        final Comment comment5 = this.commentRepository.save(new Comment(post2, "author", "content-5"));
        final Comment comment6 = this.commentRepository.save(new Comment(post2, "author", "content-6"));

        // ACT
        Iterable<Comment> byPostId = this.commentRepository.findAll(CommentRepository.Predicates.byPostId(post1.getId()));

        // ASSERT
        assertThat(byPostId).as("byPostId predicates").isNotNull().hasSize(3);
        assertThat(byPostId).extracting("id").as("byPostId predicates")
                .containsOnly(comment1.getId(), comment2.getId(), comment3.getId());
    }

    @Test
    public void checkPredicates2() throws Exception {

        // ARRANGE
        final Post post1 = this.postRepository.save(new Post("author", "post-1", "content-1"));
        final Post post2 = this.postRepository.save(new Post("author", "post-2", "content-2"));

        final Comment comment1 = this.commentRepository.save(new Comment(post1, "author", "content-1"));
        final Comment comment2 = this.commentRepository.save(new Comment(post1, "author", "content-2"));
        final Comment comment3 = this.commentRepository.save(new Comment(post1, "author", "content-3"));
        final Comment comment4 = this.commentRepository.save(new Comment(post2, "author", "content-4"));
        final Comment comment5 = this.commentRepository.save(new Comment(post2, "author", "content-5"));
        final Comment comment6 = this.commentRepository.save(new Comment(post2, "author", "content-6"));

        // ACT
        Iterable<Comment> byPostId = this.commentRepository.findAll(CommentRepository.Predicates.byPostId(post1.getId()));

        // ASSERT
        assertThat(byPostId).as("byPostId predicates").isNotNull().hasSize(3);
        assertThat(byPostId).extracting("id").as("byPostId predicates")
                .containsOnly(comment1.getId(), comment2.getId(), comment3.getId());
    }

}