package fr.patouche.soat;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class LiquibaseHelperTest {

    private EmbeddedDatabase dataSource;

    @Before
    public void setUp() {
        this.dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:/h2-reset.sql")
                .build();
    }

    public Set<String> getTables() throws SQLException {
        Set<String> tables = new HashSet<>();
        try (ResultSet resultSet = this.dataSource.getConnection().getMetaData().getTables(null, null, null, null)) {
            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error", e);
        }
        return tables;
    }

    @Test
    public void check_whenDatabaseIsEmpty() throws Exception {

        // ARRANGE
        final LiquibaseHelper liquibase = new LiquibaseHelper(this.dataSource);

        // ACT
        Collection<String> changeSets = liquibase.check();

        // ASSERT
        assertThat(changeSets).as("list unrun changeSets")
                .isNotNull()
                .contains("Changelog : '1444232542055-8' by patouche in file 'db.changelog-1.0.xml'");
    }

    @Test
    public void check_whenDatabaseIsUpToDate() throws Exception {

        // ARRANGE
        final LiquibaseHelper liquibase = new LiquibaseHelper(this.dataSource).update();

        // ACT
        Collection<String> changeSets = liquibase.check();

        // ASSERT
        assertThat(changeSets).as("No unrun changeSets")
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void update() throws Exception {

        // ARRANGE
        final LiquibaseHelper liquibase = new LiquibaseHelper(this.dataSource);
        assertThat(getTables()).as("tables created")
                .doesNotContain("DATABASECHANGELOG", "DATABASECHANGELOGLOCK", "COMMENT", "POST");

        // ACT
        liquibase.update();

        // ASSERT
        assertThat(getTables()).as("tables created")
                .contains("DATABASECHANGELOG", "DATABASECHANGELOGLOCK", "COMMENT", "POST");
    }
}