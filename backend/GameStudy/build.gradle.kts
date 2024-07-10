plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "ru.nsu"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	//developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	//implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")
	//implementation("io.swagger.core.v3:swagger-annotations:2.2.1")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.security:spring-security-web")
	implementation("org.postgresql:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security:spring-security-config")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")


	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.7.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude("org.junit.vintage");
		exclude("junit.vintage.engine");
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
