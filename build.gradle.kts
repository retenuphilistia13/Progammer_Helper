

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
    implementation("org.json:json:20230227")

}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.programmerhelper.ProgrammerHelper" // Use the main class from the application block
    }
    // Include dependencies in the JAR file
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
    useJUnitPlatform()
}



