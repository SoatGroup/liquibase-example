package fr.patouche.soat;

import javax.sql.DataSource;
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
    protected Liquibase retrieveLiquibase() {
        try {
            JdbcConnection connection = new JdbcConnection(this.dataSource.getConnection());
            return new Liquibase(DB_CHANGELOG, new ClassLoaderResourceAccessor(), connection);
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Cannot retrieve a Liquibase instance.", e);
        }
    }

    /**
     * List all changeSets that hasn't run yet.
     *
     * @return all changeSets that hasn't run.
     */
    public List<String> check() {
        try {
            return this.retrieveLiquibase().listUnrunChangeSets(null, null)
                    .stream()
                    .map((c) -> "Changelog : '" + c.getId() + "' by " + c.getAuthor() + " in file '" + c.getFilePath() + "'")
                    .collect(Collectors.toList());
        } catch (LiquibaseException e) {
            throw new RuntimeException("Error during liquibase operation.", e);
        }
    }

    /**
     * List all changeSets that hasn't run yet. This will throw a {@link java.lang.RuntimeException} if one changeSets hasn't run.
     *
     * @return the current instance
     */
    public LiquibaseHelper checkAndFail() {
        List<String> changeSets = this.check();
        if (changeSets.size() > 0) {
            throw new RuntimeException("All changeSets have not been executed. Missing changeSets to execute : \n" + changeSets);
        }
        return this;
    }

    /**
     * Update the database.
     *
     * @return the current instance
     */
    public LiquibaseHelper update() {
        try {
            this.retrieveLiquibase().update(new Contexts());
        } catch (LiquibaseException e) {
            throw new RuntimeException("Error during liquibase operation.", e);
        }
        return this;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}
