package org.iamsteveholmes.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ScroogePlugin implements Plugin<Project> {

    @Override
    void apply(final Project project) {
        project.tasks.add("compileScrooge", org.iamsteveholmes.gradle.ScroogeCompileTask)

        project.afterEvaluate {
            project.tasks.compileScrooge.execute()
        }
    }
}
