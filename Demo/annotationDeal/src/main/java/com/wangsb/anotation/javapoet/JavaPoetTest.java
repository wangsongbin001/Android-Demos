package com.wangsb.anotation.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

public class JavaPoetTest {

    public static void main(String args[]){
        System.out.print("hello java ");
        /**
         * generate JavaPoetTest2
         * 1,generate func
         * 2,generate type
         * 3,generate file
         */
        MethodSpec main = MethodSpec
                .methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("$T.out.print($S)", System.class, "hello JavaPoet")
                .returns(TypeName.VOID)
                .build();

        TypeSpec HelloWorld = TypeSpec
                .classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile
                .builder("com.wangsb.anotation", HelloWorld)
                .build();

//        try {
//            Filer filer = ProcessingEnvironment.getFiler();
//            javaFile.writeTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
