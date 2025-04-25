package jp.co.suda.common;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceProperties.class)
public class FlywayConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource")
  public DataSource dataSource(DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Bean(initMethod = "migrate")
  public Flyway createFlyway(DataSource dataSource) {
    return new Flyway(new FluentConfiguration().locations("db/migration/ddl"));
  }
}
