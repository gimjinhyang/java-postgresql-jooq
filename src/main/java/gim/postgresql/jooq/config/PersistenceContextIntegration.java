package gim.postgresql.jooq.config;

import org.jooq.SQLDialect;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(TransactionAutoConfiguration.class)
public class PersistenceContextIntegration {

  private final DataSource dataSource;

  public PersistenceContextIntegration(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public DefaultConfiguration configuration() {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(dataSourceConnectionProvider());
    jooqConfiguration.set(springTransactionProvider());
    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
    jooqConfiguration.set(SQLDialect.POSTGRES);
    jooqConfiguration.set(settings());
    return jooqConfiguration;
  }

  @Bean
  public DataSourceConnectionProvider dataSourceConnectionProvider() {
    return new DataSourceConnectionProvider(transactionAwareDataSource());
  }

  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(configuration());
  }

  @Bean
  public ExceptionTranslator exceptionTransformer() {
    return new ExceptionTranslator();
  }

  @Bean
  public Settings settings() {
    return new Settings().withRenderKeywordStyle(RenderKeywordStyle.LOWER)
                         .withStatementType(StatementType.STATIC_STATEMENT)
                         .withRenderFormatted(true);
  }

  @Bean
  public SpringTransactionProvider springTransactionProvider() {
    return new SpringTransactionProvider(transactionManager());
  }

  @Bean
  public TransactionAwareDataSourceProxy transactionAwareDataSource() {
    return new TransactionAwareDataSourceProxy(dataSource);
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }

}