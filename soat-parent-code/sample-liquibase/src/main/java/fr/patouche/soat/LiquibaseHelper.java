package fr.patouche.soat;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Liquibase class.
 *
 * @author patouche - 08/10/15.
 */
public class LiquibaseHelper {

    /** Path for DB Changelog. */
    private static final String DB_CHANGELOG = "changelog/db.changelog-master.xml";

    /** The datasource to use. */
    private final DataSource dataSource;

    /**
     * Class constructor.
     *
     * @param dataSource the sql dataSource to use for liquibase.
     */
    public LiquibaseHelper(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieve a liquibase instance.
     *
     * @return the liquibase instance.
     */
    protected <O> O liquibaseResult(final LiquibaseAction<O> action) {
        try (final Connection c = this.dataSource.getConnection()) {
            final Liquibase liquibase = new Liquibase(DB_CHANGELOG, new ClassLoaderResourceAccessor(), new JdbcConnection(c));
            return action.execute(liquibase);
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Error during liquibase execution", e);
        }
    }

    /**
     * List all changeSets that hasn't run yet.
     *
     * @return all changeSets that hasn't run.
     */
    public List<String> check() {
        return this.liquibaseResult((l) -> l.listUnrunChangeSets(null, null)
                .stream()
                .map((c) -> "Changelog : '" + c.getId() + "' by " + c.getAuthor() + " in file '" + c.getFilePath() + "'")
                .collect(Collectors.toList()));
    }

    /**
     * List all changeSets that hasn't run yet. This will throw a {@link java.lang.RuntimeException} if one changeSets hasn't run.
     *
     * @return the current instance
     */
    public LiquibaseHelper checkAndFail() {
        List<String> changeSets = this.check();
        if (changeSets.size() > 0) {
            throw new RuntimeException("All changeSets have not been executed. Missing changeSets to liquibaseExecute : \n" + changeSets);
        }
        return this;
    }

    /**
     * Update the database.
     *
     * @return the current instance
     */
    public LiquibaseHelper update() {
        return this.liquibaseResult((l) -> {
            l.update(new Contexts());
            return this;
        });
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Execute a action on liquibase and return the expected output.
     *
     * @param <O> the type of output.
     */
    @FunctionalInterface
    interface LiquibaseAction<O> {

        /**
         * Execute a liquibase action
         *
         * @param liquibase the liquibase instance
         * @return the expected output.
         * @throws LiquibaseException if a error occurred during liquibase execution
         */
        O execute(Liquibase liquibase) throws LiquibaseException;

    }

}
