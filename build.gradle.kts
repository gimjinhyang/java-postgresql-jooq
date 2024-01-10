import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target

plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "gim"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:3.2.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.1")

    compileOnly("org.jooq:jooq:3.18.8")

    runtimeOnly("org.postgresql:postgresql:42.2.16")
}


buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath("org.jooq:jooq-codegen:3.18.8")
        classpath("org.postgresql:postgresql:42.2.16")
    }
}


GenerationTool.generate(
    Configuration().withJdbc(
        Jdbc()
            .withDriver("org.postgresql.Driver")
            .withUrl("jdbc:postgresql://localhost:5432/jinny")
            .withUser("jinny")
            .withPassword("")
    ).withGenerator(
        Generator()
            .withDatabase(Database())
            .withGenerate(
                Generate()
                    .withPojos(true)
                    .withDaos(true)
            )
            .withTarget(
                Target()
                    .withPackageName("gim.postgresql.jooq.model")
                    .withDirectory("src/main/java")
            )
    )
)


//jooq {
//    configuration {
//        logging = Logging.WARN
//
//        jdbc {
//            driver = "org.postgresql.Driver"
//            url = "jdbc:postgresql://localhost:5432/jinny"
//            user = "jinny"
//            password = ""
//        }
//
//        generator {
//            name = "org.jooq.codegen.JavaGenerator"
//
//            database {
//                name = "org.jooq.meta.postgres.PostgresDatabase"
//                includes = ".*"
//                excludes = ""
//                inputSchema = "public"
//            }
//
//            generator { }
//            target {
//                packageName = "gim.postgresql.jooq.model"
//                directory = "src/main/java"
//            }
//
//            generate {
//            }
//        }
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
}
