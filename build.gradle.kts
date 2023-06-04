plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.1")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.1")
}

tasks.test {
    useJUnitPlatform()
}