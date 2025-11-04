/**
 * Plugins utilisés dans le projet.
 */
plugins {
    java
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.dokka") version "1.9.20"
}

/** Groupe et version du projet. */
group = "com.vehiclerental"
version = "1.0.0"

/** Version Java utilisée. */
java.sourceCompatibility = JavaVersion.VERSION_17

/**
 * Repositories pour les dépendances.
 */
repositories {
    mavenCentral()
}

/**
 * Dépendances du projet.
 */
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

/** Configuration des tests pour JUnit Platform. */
tasks.withType<Test> {
    useJUnitPlatform()
}

/** Configuration du compilateur Kotlin. */
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

/**
 * Tâche Dokka personnalisée pour générer la documentation HTML.
 */
tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtmlCustom") {
    /** Répertoire de sortie. */
    outputDirectory.set(buildDir.resolve("dokka"))
    dokkaSourceSets {
        named("main") {
            /** Fichier README à inclure dans la documentation. */
            includes.from("README.md")
            /** Signaler les éléments non documentés. */
            reportUndocumented.set(true)
            /** Ignorer les packages vides. */
            skipEmptyPackages.set(true)
            /** Version JDK utilisée pour la doc. */
            jdkVersion.set(17)
            /** Lien vers le code source pour navigation dans la doc. */
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    uri("https://github.com/StailiSaad/GESTION-DE-LOCATION-DE-VEHICULES-BACK-END/tree/main/src/main/kotlin")
                        .toURL()
                )
            }
        }
    }
}
