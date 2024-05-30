plugins {
	java
	war
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "org.muztache"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {

	compileOnly("org.projectlombok:lombok:1.18.32")
	annotationProcessor("org.projectlombok:lombok:1.18.32")

	testCompileOnly("org.projectlombok:lombok:1.18.32")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.squareup.okhttp3:okhttp:4.9.3")

	implementation("org.springframework.boot:spring-boot-docker-compose:3.1.1")

	compileOnly("io.jsonwebtoken:jjwt-api:0.12.3")
	compileOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	compileOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.3")

	implementation("commons-io:commons-io:2.16.1")

	implementation("com.cloudinary:cloudinary:1.0.2")

	runtimeOnly("org.postgresql:postgresql")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
