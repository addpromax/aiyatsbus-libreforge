group = "com.mcstarrysky"
version = rootProject.version

gradle.buildFinished {
    buildDir.deleteRecursively()
}