## Board Project 입니다.

## Spring 실행 시
### java.lang.NullPointerException: Cannot invoke "org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(java.sql.SQLException, String)" because the return value of "org.hibernate.resource.transaction.backend.jdbc.internal.JdbcIsolationDelegate.sqlExceptionHelper()" is null
### 해당 에러 해결이 필요 !

## AWS ElasticBeanstalk 을 구성 !

## RDS 생성 후
## CREATE DATABASE BOARD; BOARD 데이터 베시으 생성 후 Schema 를 조회 SHOW SCHEMAS;




// TODO - 왜 아래 build gradle script 는 에러가 발생할 까 .... ! 원인 파악이 필요 ... !!!
//plugins {
//    id("org.springframework.boot") version "3.3.2"
//    id("io.spring.dependency-management") version "1.1.6"
//    id("org.jlleitschuh.gradle.ktlint-idea") version "11.4.2"
//
//    // all-open 확인이 필요 ... ! kotlin 에서 사용 시 !!
//    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.20-RC"
//
//    kotlin("plugin.jpa") version "1.9.24"
//    kotlin("jvm") version "1.9.24"
//    kotlin("plugin.spring") version "1.9.24"
//    kotlin("kapt") version "2.0.0"
//}
//
//group = "com.project"
//version = "0.0.1-SNAPSHOT"
//
//java {
//    toolchain {
//        languageVersion = JavaLanguageVersion.of(17)
//    }
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
////    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
////    implementation("org.springframework.boot:spring-boot-starter-web")
////    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
////    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
////    implementation("com.querydsl:querydsl-jpa")
////
////    kapt("com.querydsl:querydsl-apt")
////
////    implementation("org.jetbrains.kotlin:kotlin-reflect")
////    implementation("com.mysql:mysql-connector-j:8.3.0")
////    // runtimeOnly("com.mysql:mysql-connector-j")
////
////    testImplementation("org.springframework.boot:spring-boot-starter-test")
////    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
////    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
////    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
////    testImplementation("com.h2database:h2")
//
//
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
//    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
//    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
//    runtimeOnly("com.mysql:mysql-connector-j")
//
//    testImplementation("com.h2database:h2")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
//    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
//    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
//    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")
//
//}
//
//kotlin {
//    compilerOptions {
//        freeCompilerArgs.addAll("-Xjsr305=strict")
//    }
//}
//
//// plain jar 파일이 생성되지 않게 함 !
//// 일반적으로 gradle 이 jar 파일을 빌드하면 2개 jar 가 생성이 되는데 plain jar 를 생성하지 않게 함 !
//tasks.named<Jar>("jar") {
//    enabled = false
//}
//
//
//tasks.withType<Test> {
//    useJUnitPlatform()
//}
