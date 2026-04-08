/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2025 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

// Fabric subproject – targets Minecraft 26.1.1 + Create Fly
// NOTE: version numbers marked TODO must be verified against available releases.

plugins {
    java
    id("fabric-loom") version "1.15-SNAPSHOT"
}

// -------------------------------------------------------------------------
// Helpers
// -------------------------------------------------------------------------
operator fun String.invoke(): String =
    rootProject.findProperty(this) as? String
        ?: throw IllegalStateException("Property $this is not defined")

// -------------------------------------------------------------------------
// Source sets – include filtered common sources + fabric-specific sources
// -------------------------------------------------------------------------

// Copy common code (excluding NeoForge-specific packages) to a build
// directory so that only the neoforge/ package from fabric/src takes effect.
val copyFilteredCommonJava = tasks.register<Copy>("copyFilteredCommonJava") {
    from("${rootProject.projectDir}/src/main/java") {
        // Exclude NeoForge-only packages; the fabric/src replaces them.
        exclude("**/neoforge/**")
    }
    into(layout.buildDirectory.dir("filtered-common/java"))
}

val copyFilteredCommonResources = tasks.register<Copy>("copyFilteredCommonResources") {
    // Include all common resources except NeoForge metadata files.
    from("${rootProject.projectDir}/src/main/resources") {
        exclude("META-INF/mods.toml")
        exclude("META-INF/neoforge.mods.toml")
        exclude("META-INF/services/**")
        // Exclude NeoForge-specific mixin config (replaced by fabric one)
        exclude("railways.mixins.json")
    }
    from("${rootProject.projectDir}/src/generated/resources") {
        exclude(".cache/**")
    }
    into(layout.buildDirectory.dir("filtered-common/resources"))
}

sourceSets {
    main {
        java {
            // Filtered common sources (no neoforge/ package)
            srcDir(copyFilteredCommonJava.map { it.destinationDir })
            // Fabric-specific sources (provides neoforge/ replacements + Fabric APIs)
            srcDir("src/main/java")
        }
        resources {
            srcDir(copyFilteredCommonResources.map { it.destinationDir })
            // Fabric-specific resources (fabric.mod.json, mixin config, etc.)
            srcDir("src/main/resources")
        }
    }
}

tasks.compileJava {
    dependsOn(copyFilteredCommonJava, copyFilteredCommonResources)
}
tasks.processResources {
    dependsOn(copyFilteredCommonResources)
}

// -------------------------------------------------------------------------
// Project metadata
// -------------------------------------------------------------------------
group = "maven_group"()
version = "${"mod_version"()}+fabric-mc${"fabric_mc_version"()}"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

// -------------------------------------------------------------------------
// Loom / Minecraft configuration
// -------------------------------------------------------------------------
loom {
    // Use the access widener from the root project (platform-neutral widener)
    accessWidenerPath = rootProject.file("src/main/resources/railways.accesswidener")

    runs {
        named("client") {
            client()
            name("Fabric Client")
            vmArg("-Dmixin.debug.export=true")
        }
        named("server") {
            server()
            name("Fabric Server")
        }
        create("datagen") {
            client()
            name("Fabric Data Generation")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${rootProject.file("src/generated/resources")}")
            vmArg("-Dfabric-api.datagen.modid=railways")
            environmentVariable("DATAGEN", "TRUE")
        }
    }
}

// -------------------------------------------------------------------------
// Dependencies
// -------------------------------------------------------------------------
repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://api.modrinth.com/maven") { name = "Modrinth" }
    maven("https://jm.gserv.me/repository/maven-public/") { name = "JourneyMap" }
    maven("https://maven.maxhenkel.de/repository/public") { name = "Simple Voice Chat" }
    maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
    maven("https://maven.createmod.net") { name = "CreateMod" }
    // Registrate-Refabricated fallback repos (provided transitively by Create Fly;
    // these are only consulted if Create Fly does not include Registrate itself).
    maven("https://mvn.devos.one/snapshots/") { name = "devos snapshots (Registrate)" }
    maven("https://maven.tterrag.com/") { name = "tterrag (Registrate)" }
    // NightConfig / electronwill – provides TOML/JSON config file support.
    // Bundled by Create Fly; declaring mavenCentral here as a fallback.
    mavenCentral()
}

@Suppress("UnstableApiUsage")
dependencies {
    // Minecraft
    minecraft("com.mojang:minecraft:${"fabric_mc_version"()}")
    // Mojang mappings (Parchment is not yet available for 26.x; use plain mojmap)
    // TODO: switch to parchment when available for MC 26.1.1
    @Suppress("UnstableApiUsage")
    mappings(loom.officialMojangMappings())

    // Fabric Loader
    modImplementation("net.fabricmc:fabric-loader:${"fabric_loader_version"()}")

    // Fabric API for MC 26.1.1
    // TODO: verify exact version at https://fabricmc.net/develop/
    modImplementation("net.fabricmc.fabric-api:fabric-api:${"fabric_api_version"()}")

    // Create Fly – the Fabric port of Create targeting MC 26.1.1.
    // Resolved from the Modrinth Maven repository.
    // Create Fly bundles Registrate-Refabricated and NightConfig transitively,
    // so no separate Registrate or nightconfig dependency is needed.
    modImplementation("maven.modrinth:create-fly:${"create_fly_version"()}")

    // MixinExtras (common + fabric include)
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:${"mixin_extras_version"()}")!!)!!
    implementation(include("io.github.llamalad7:mixinextras-fabric:${"mixin_extras_version"()}")!!)!!

    // NightConfig – TOML/JSON config file support, used by our TOML-backed ModConfigSpec.
    // Provided transitively by Create Fly; declared explicitly as a fallback to ensure
    // it is always on the compile classpath even before Create Fly resolves.
    implementation("com.electronwill.night-config:toml:3.6.7")
    implementation("com.electronwill.night-config:core:3.6.7")
}

// -------------------------------------------------------------------------
// Resource filtering: expand placeholders in fabric.mod.json
// -------------------------------------------------------------------------
tasks.processResources {
    val properties = mapOf(
        "version" to project.version.toString(),
        "fabric_mc_version" to "fabric_mc_version"(),
        "fabric_api_version" to "fabric_api_version"(),
        "fabric_loader_version" to "fabric_loader_version"(),
        "create_fly_version" to "create_fly_version"()
    )
    inputs.properties(properties)
    filesMatching("fabric.mod.json") {
        expand(properties)
    }
}
