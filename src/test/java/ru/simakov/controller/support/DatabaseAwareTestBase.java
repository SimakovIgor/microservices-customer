package ru.simakov.controller.support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public abstract class DatabaseAwareTestBase {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;
    protected abstract String getSchema();

    protected abstract Set<String> getTables();

    @BeforeEach
    void check() {
        getTables().stream()
                .map(this::countRecordsInTable)
                .forEach(count -> assertThat(count).isZero());
    }

    @AfterEach
    void truncateTables() {
        jdbcTemplate.execute("truncate table " + getTables().stream()
                .map(this::tableNameWithSchema)
                .collect(Collectors.joining(", ")));
    }

    protected long countRecordsInTable(String tableName) {
        final var queryResult = jdbcTemplate.queryForObject(
                "select count(*) from " + tableNameWithSchema(tableName), Long.class);
        return Objects.requireNonNullElse(queryResult, 0L);
    }

    protected String tableNameWithSchema(String tableName) {
        var schema = getSchema();
        return tableName.startsWith(schema) ? tableName : String.format("%s.%s", schema, tableName);
    }

}
