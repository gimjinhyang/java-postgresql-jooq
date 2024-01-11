package gim.postgresql.jooq.service;

import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

import gim.postgresql.jooq.model.tables.records.MemberRecord;
import lombok.extern.slf4j.Slf4j;

import static gim.postgresql.jooq.model.Tables.MEMBER;

@Slf4j
@JooqTest
class MemberServiceTest {

  @Autowired
  private DSLContext dsl;

  //  @Disabled
  @Test
  public void test() {
    final @Nullable MemberRecord member = dsl.selectFrom(MEMBER).limit(1).fetchOne();
    log.info("======================================= member : {}", member.toString());
  }


}
