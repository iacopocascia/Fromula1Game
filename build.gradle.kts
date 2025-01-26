plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //Jackson library dependencies
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
}
application{
    mainClass.set("it.unicam.formula1Game.application.Formula1ApplicationCpu")
}

tasks.test {
    useJUnitPlatform()
}
// Custom task to handle proper input redirection for interactive applications
tasks.register<JavaExec>("runApp"){
    group = "application"
    description = "Run the Formula1Game application with proper input handling"
    mainClass.set("it.unicam.formula1Game.application.Formula1ApplicationCpu")
    classpath = sourceSets.main.get().runtimeClasspath
    standardInput = System.`in` // Redirects input to System.in
}