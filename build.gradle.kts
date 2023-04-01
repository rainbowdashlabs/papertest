import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0-SNAPSHOT"
}

group = "de.chojo"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://eldonexus.de/repository/maven-public/")
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    paperLibrary("net.kyori", "adventure-platform-bukkit", "4.2.0")
    paperLibrary("de.eldoria.util", "jackson-configuration", "2.0.0-SNAPSHOT")

    testImplementation("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks {
    register<Copy>("copyToServer") {
        val path = project.property("targetDir") ?: ""
        if (path.toString().isEmpty()) {
            println("targetDir is not set in gradle properties")
            return@register
        }
        from(jar)
        destinationDir = File(path.toString())
    }
            test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }

}

paper {
    main = "de.chojo.Main"
    bootstrapper = "de.chojo.Bootstrapper"
    loader = "de.chojo.Loader"

    generatePluginLibraries = true

    foliaSupported = true

    apiVersion = "1.19"

    depends {
        register("WorldEdit") {
            required = true
        }
        register("Essentials") {
            required = true
        }
    }

    loadBefore {
        register("BrokenPlugin") {
            bootstrap = true
        }
    }

    commands {
        register("test") {
            description = "This is a test command!"
            aliases = listOf("t")
            permission = "testplugin.test"
            usage = "Just run the command!"
            // permissionMessage = "You may not test this command!"
        }
        // ...
    }

    permissions {
        register("testplugin.*") {
            children = listOf("testplugin.test") // Defaults permissions to true
            // You can also specify the values of the permissions
            childrenMap = mapOf("testplugin.test" to true)
        }
        register("testplugin.test") {
            description = "Allows you to run the test command"
            default = PaperPluginDescription.Permission.Default.OP // TRUE, FALSE, OP or NOT_OP
        }
    }
}
