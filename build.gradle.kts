import org.jooq.meta.jaxb.Logging

plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jooq.jooq-codegen-gradle") version "3.19.1"
}

group = "gim"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:3.2.1")

    runtimeOnly("org.postgresql:postgresql:42.2.16")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.1")

    jooqCodegen("org.postgresql:postgresql:42.2.16")
}

jooq {
    configuration {
        logging = Logging.WARN

        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/jinny"
            user = "jinny"
            password = ""
        }

        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = ".*"
                excludes = ""
                inputSchema = "public"
            }
            generate {
                isPojos = true
                isDaos = true
            }
            target {
                packageName = "gim.postgresql.jooq.model"
                directory = "src/main/java"
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
