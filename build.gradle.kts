// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Classpath.kotlin)
        classpath(Classpath.gradle)
        classpath(Classpath.hilt)
        //classpath(Classpath.googleServices)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        // Add the Crashlytics Gradle plugin
        //classpath(Classpath.firebase)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url  = uri("https://oss.jfrog.org/libs-snapshot")
        }
    }
}

tasks.register("clean").configure {
    delete("build")
}
