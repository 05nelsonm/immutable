/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.binary.compat)
    alias(libs.plugins.kotlin.multiplatform) apply(false)
}

allprojects {

    findProperty("GROUP")?.let { group = it }
    findProperty("VERSION_NAME")?.let { version = it }
    findProperty("POM_DESCRIPTION")?.let { description = it.toString() }

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

}

plugins.withType<YarnPlugin> {
    the<YarnRootExtension>().lockFileDirectory = rootDir.resolve(".kotlin-js-store")
}

plugins.withType<NodeJsRootPlugin> {
    the<NodeJsRootExtension>().apply {
        nodeVersion = "22.0.0-nightly202404032241e8c5b3"
        nodeDownloadBaseUrl = "https://nodejs.org/download/nightly"
    }

    tasks.withType<KotlinNpmInstallTask>().configureEach {
        args.add("--ignore-engines")
    }
}

apiValidation {
    if ((findProperty("CHECK_PUBLICATION") as? String) != null) {
        ignoredProjects.add("check-publication")
    }
}
