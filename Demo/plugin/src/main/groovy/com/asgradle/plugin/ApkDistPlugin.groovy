package com.asgradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkDistPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.task('apkdist') << {
            println 'hello, world!'
        }
    }

}