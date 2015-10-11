package fr.patouche.soat.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.patouche.soat.LiquibaseHelper;
import fr.patouche.soat.entity.Comment;
import fr.patouche.soat.repository.config.SpringDataJpaConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CommentRepositoryAnnotationTest.TestConfig.class, SpringDataJpaConfiguration.class })
public class CommentRepositoryAnnotationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            final EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
            return new LiquibaseHelper(dataSource).update().getDataSource();
        }
    }

    @Test
    @Sql(scripts = { "classpath:/repository/clean.sql", "classpath:/repository/comment/checkPredicates.sql" })
    public void checkPredicates() throws Exception {

        // ACT
        Iterable<Comment> byPostId = this.commentRepository.findAll(CommentRepository.Predicates.byPostId(2L));
        assertThat(byPostId).as("byPostId predicates").isNotNull().hasSize(1);
        assertThat(byPostId).extracting("content").as("byPostId predicates").containsOnly("content-3");

    }

    @Test
    @Sql(scripts = { "classpath:/repository/clean.sql", "classpath:/repository/comment/checkPredicates.sql" })
    public void checkPredicates2() throws Exception {

        // ACT
        Iterable<Comment> byPostId = this.commentRepository.findAll(CommentRepository.Predicates.byPostId(2L));
        assertThat(byPostId).as("byPostId predicates").isNotNull().hasSize(1);
        assertThat(byPostId).extracting("content").as("byPostId predicates").containsOnly("content-3");

    }
}