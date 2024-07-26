plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "app.poo_p2_g01_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "app.poo_p2_g01_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        //Para configuraciones de minSDKVersion 20 o menores
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Flag para habilitar el soporte de nuevas APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}