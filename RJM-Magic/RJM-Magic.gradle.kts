version = "2.1.0"

project.extra["PluginName"] = "RJM-Magic" // This is the name that is used in the external plugin manager panel
project.extra["PluginDescription"] = "Blows glass items at clan bank or north fossil island" // This is the description that is used in the external plugin manager panel

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}