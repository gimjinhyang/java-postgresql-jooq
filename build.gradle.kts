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
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.1")
    testImplementation("org.jetbrains:annotations:24.0.0")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("org.jooq:jooq:3.19.1")


    runtimeOnly("org.postgresql:postgresql:42.2.16")
    jooqCodegen("org.postgresql:postgresql:42.2.16")
}

val dbUrl: String? = System.getenv("DB_URL")
val dbUser: String? = System.getenv("DB_USER")
val dbPassword: String? = System.getenv("DB_PASSWORD")

jooq {
    configuration {
        logging = Logging.WARN
        jdbc {
            driver = "org.postgresql.Driver"
            url = dbUrl
            user = dbUser
            password = dbPassword
        }
        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = ".*"
                excludes = ""
                inputSchema = "public"
            }
            generate {}
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

tasks.all {
//    println(name)
    if (name.equals("jooqCodegen")) {
        // disable auto generation jooq
        enabled = false
    }
}
