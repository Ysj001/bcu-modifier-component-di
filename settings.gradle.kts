rootProject.name = "BytecodeUtil"

// component di modifier
include(":lib_modifier_component_di")
include(":lib_modifier_component_di:component-di-api")

private val hasPlugin = File(rootDir, "gradle.properties")
    .inputStream()
    .use { java.util.Properties().apply { load(it) } }
    .getProperty("bcu.groupId")
    .replace(".", File.separator)
    .let { File(File(rootDir, "repos"), it) }
    .run { isDirectory && !list().isNullOrEmpty() }

if (hasPlugin) {
    // Demo
    include(":app")
    include(":demo1")
    include(":demo1:demo1-api")
}
