import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
    //id("com.google.gms.google-services")
    //id("com.google.firebase.crashlytics")
}

//val secrets = getSecretsProperties()

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "tour.donnees.journal"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 7
        versionName = "0.0.7"

        testInstrumentationRunner = "tour.donnees.journal.base.BaseTestRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias("debub")
            keyPassword("android")
            storeFile = file("../debug.keystore")
            storePassword("android")
        }

        /*create("release") {
            keyAlias(secrets.getProperty(SecretsProperties.KEY_ALIAS))
            keyPassword(secrets.getProperty(SecretsProperties.KEY_PASS))
            storeFile = file((secrets.getProperty(SecretsProperties.KEY_LOCAL)))
            //storeFile = file((secrets.getProperty(SecretsProperties.KEY_LOCAL_PROD)))
            storePassword(secrets.getProperty(SecretsProperties.STORE_PASS))
        }*/
    }

    productFlavors {

        create("prod") {
            dimension("environment")
            buildConfigField("String", "ENDPOINT", project.property("ENDPOINT_PROD").toString())
            resValue("string","app_name", "Journal")
            resValue("color","app_color", "#FFFFFFFF")
            resValue("color","app_color_letter", "#000000")

        }

        create("stage") {
            dimension("environment")
            versionNameSuffix("-stage")
            applicationIdSuffix(".stage")
            buildConfigField("String", "ENDPOINT", project.property("ENDPOINT_STAGE").toString())
            resValue("string","app_name", "Journal [STAGE]")
            resValue("color","app_color", "#FFBB86FC")
            resValue("color","app_color_letter", "#FFFFFFFF")
        }

        create("dev") {
            dimension("environment")
            versionNameSuffix("-dev")
            applicationIdSuffix(".dev")
            buildConfigField("String", "ENDPOINT", project.property("ENDPOINT_DEV").toString())
            resValue("string","app_name", "Journal [DEV]")
            resValue("color","app_color", "#FF018786")
            resValue("color","app_color_letter", "#FFFFFFFF")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    flavorDimensions("environment")

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isDebuggable = true
        }

        /*getByName("debug") {
            isMinifyEnabled = true
            isDebuggable = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }*/

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
    }

    lintOptions {
        disable("ObsoleteLintCustomCheck")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:28.4.2"))


    // Add the dependency for the Firebase SDK for Google Analytics
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")



    implementation(Kotlin.kotlinStdLib)

    implementation(AndroidX.androidCoreKotlin)
    implementation(AndroidX.activity)
    implementation(AndroidX.fragment)
    implementation(AndroidX.lifecycle)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.material)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.coordinatorLayout)
    implementation(AndroidX.pageView2)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.recyclerViewSelection)
    implementation(AndroidX.legacyV4)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltAndroidCompiler)

    implementation(OkHttp.okHttp)
    implementation(OkHttp.okHttpLoggingInterceptor)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConverterGson)
    implementation(Retrofit.gson)

    implementation(Picasso.picasso)

    implementation(RxJava.rxAndroid)
    implementation(RxJava.rxJava)
    implementation(RxJava.rxJavaRetrofitAdapter)

    implementation(Room.runtime)
    kapt (Room.compiler)

    /**
     * Test
     */

    testImplementation("io.mockk:mockk:1.12.0")


    //testImplementation("androidx.arch.core:core-common:2.1.0")
    //testImplementation("androidx.arch.core:core-runtime:2.1.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    testImplementation(OkHttp.mockWebServer)

    testImplementation(Hilt.hiltAndroidTest)
    kaptTest(Hilt.hiltAndroidCompiler)

    androidTestImplementation(Hilt.hiltAndroidTest)
    kaptAndroidTest(Hilt.hiltAndroidCompiler)

    testImplementation(TestLibs.junitTest)
    testImplementation(Room.roomTest)

    androidTestImplementation(AndroidTestLibs.androidTestJunitTest)
    androidTestImplementation(AndroidTestLibs.androidEspressoCoreTest)

}

kapt {
    correctErrorTypes = true
}

fun getSecretsProperties(): Properties {
    val secretsPropertiesFile = rootProject.file("./secrets.properties")
    val secretsProperties = Properties()
    secretsProperties.load(FileInputStream(secretsPropertiesFile))
    return secretsProperties
}

