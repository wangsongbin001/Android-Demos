package com.wangsb.group.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkDistPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.extensions.create('apkdistconf', ApkDistExtension);
        project.task('renameApk') << {
            println 'hello, world!'
            def nameMap = project['apkdistconf'].nameMap;
            if (nameMap != null ) {
                nameMap('wow');
            }
            println project['apkdistconf'].destDir;
        }
        project.afterEvaluate {
            //只可以在 android application 或者 android lib 项目中使用
            if (!project.android) {
                throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!')
            }

            //配置不能为空
            if (project.apkdistconf.nameMap == null || project.apkdistconf.destDir == null) {
                project.logger.info('Apkdist conf should be set!')
                return
            }

            Closure nameMap = project['apkdistconf'].nameMap
            String destDir = project['apkdistconf'].destDir

            //枚举每一个 build variant
            project.android.applicationVariants.all { variant ->
                variant.outputs.all { output ->

                    //AS升级到3.0后无法这样修改
                    //File file = output.outputFile
                    //output.outputFile = new File(destDir, nameMap(file.getName())
                    def fileName = "${project.name}_${output.baseName}-${variant.versionName}.apk"
                    outputFileName = fileName
//                    def buildName = "Downloader"
//                    def type = variant.buildType.name
//                    def releaseApkName = buildName + '_' + type + "_" + versionName + '_' + getTime() + '.apk'
//                    outputFileName = releaseApkName
//                    variant.packageApplication.outputDirectory = new File("./apk")
                }
            }
        }
    }

}