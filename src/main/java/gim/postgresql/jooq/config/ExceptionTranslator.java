package gim.postgresql.jooq.config;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.io.Serial;

public class ExceptionTranslator implements ExecuteListener {

  @Serial
  private static final long serialVersionUID = 4029640845546114318L;

  @Override
  public void exception(ExecuteContext context) {
    SQLDialect dialect = context.configuration().dialect();
    SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName());

    context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()));
  }

}
