
object Versions {
    const val coordinator = "1.1.0"
    const val okHttp = "4.9.1"
    const val selection = "1.1.0"
    const val recycler = "1.2.1"
    const val legacy = "1.0.0"
    const val picasso = "2.71828"
    const val kotlin = "1.4.32"
    const val androidCore = "1.3.2"
    const val androidCompat = "1.3.1"
    const val androidMaterial = "1.3.0"
    const val androidRoom = "2.2.5"
    const val retrofit = "2.9.0"
    const val retrofitConverterGson = "2.8.1"
    const val gson = "2.8.6"
    const val rxjava = "3.0.0"
    const val junit = "4.13.1"
    const val androidTextExt = "1.1.2"
    const val espresso = "3.3.0"
    const val hilt = "2.35.1"
    const val pageView2 = "1.0.0"
}


object Kotlin {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

}

object AndroidX {
    const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:${Versions.selection}"
    const val androidCoreKotlin = "androidx.core:core-ktx:${Versions.androidCore}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidCompat}"
    const val material = "com.google.android.material:material:${Versions.androidMaterial}"
    const val activity = "androidx.activity:activity-ktx:1.2.2"
    const val fragment = "androidx.fragment:fragment-ktx:1.3.2"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:2.0.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val pageView2 = "androidx.viewpager2:viewpager2:${Versions.pageView2}"
    const val legacyV4 = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recycler}"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinator}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object OkHttp {
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
}

object RxJava {
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxjava}"
    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val rxJavaRetrofitAdapter = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.rxjava}"
}

object Hilt {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltAndroidTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.androidRoom}"
    const val compiler = "androidx.room:room-compiler:${Versions.androidRoom}"
    const val roomTest = "androidx.room:room-testing:${Versions.androidRoom}"
    const val kotlin = "androidx.room:room-ktx:${Versions.androidRoom}"            //optional i'm not using coroutines yet
}

object TestLibs {
    const val junitTest = "junit:junit:${Versions.junit}"

}

object AndroidTestLibs {
    const val androidTestJunitTest = "androidx.test.ext:junit:${Versions.androidTextExt}"
    const val androidEspressoCoreTest = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object SecretsProperties {
    const val KEY_ALIAS = "KEY_ALIAS"
    const val STORE_PASS = "STORE_PASS"
    const val KEY_PASS = "KEY_PASS"
    const val KEY_LOCAL = "KEY_LOCAL"
    const val KEY_LOCAL_PROD = "KEY_LOCAL_PROD"
}

object Classpath {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:4.2.0"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:2.35.1"
    const val googleServices = "com.google.gms:google-services:4.3.10"
    const val firebase = "com.google.firebase:firebase-crashlytics-gradle:2.7.1"
}





