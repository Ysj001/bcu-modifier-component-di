rootProject.name = "ModifierComponentDI"

// component di modifier
include(":lib_modifier_component_di")
include(":lib_modifier_component_di:component-di-api")

private val hasPlugin = File(rootDir, "gradle.properties")
    .inputStream()
    .use { java.util.Properties().apply { load(it) } }
    .getProperty("bcu.groupId")
    .replace(".", File.separator)
    .let { File(File(rootDir, "repos"), it) }
    .takeIf { it.isDirectory }
    ?.list()
    ?.filter { it == "modifier-component-di" || it == "modifier-component-di-api"}
    .isNullOrEmpty()
    .not()

if (hasPlugin) {
    // Demo
    include(":app")
    include(":demo1")
    include(":demo1:demo1-api")
}
