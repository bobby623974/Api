pluginManagement {
    repositories {
        google {
            maven {
                url = uri("https://jitpack.io")

            }
//                url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
                maven{ url = uri("https://repo1.maven.org/maven2/") }
                maven{

                    url = uri("https://plugins.gradle.org/m2/")
                }
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            url = uri("https://jitpack.io")
        }

//                url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
            maven{
                url = uri("https://repo1.maven.org/maven2/")
            }
            maven{

                url = uri("https://plugins.gradle.org/m2/")
            }

//        jcenter()
        mavenCentral()
    }
}

rootProject.name = "ImguiLoader"
include(":app")
