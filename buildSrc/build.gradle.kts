import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    jacoco
}
// Required since Gradle 4.10+.
repositories {
    mavenCentral()
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(layout.buildDirectory.file("jacoco/jacocoTest.exec").get().asFile)
        classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}