package com.wangsb.anotation;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.xml.ws.Provider;

/**
 * Created by xiaosongshu on 2019/3/29.
 */

public class AnotationProcessor extends AbstractProcessor{

    private Filer mFiler;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> types = new LinkedHashSet<>();
        types.add(RouteAnnotation.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        HashMap<String, String> nameMap = new HashMap<>();

        Set<? extends Element> annotationElements = roundEnvironment.getElementsAnnotatedWith(RouteAnnotation.class);

        for (Element element : annotationElements) {
            RouteAnnotation annotation = element.getAnnotation(RouteAnnotation.class);
            String name = annotation.name();
            nameMap.put(name, element.getSimpleName().toString());
            //nameMap.put(element.getSimpleName().toString(), name);//MainActiviy-RouteName_MainActivity
        }

        //generate Java File
        generateJavaFile(nameMap);

        return true;
    }

    private void generateJavaFile(Map<String, String> nameMap) {
        //generate constructor
//        MethodSpec.Builder constructorBuidler = MethodSpec.constructorBuilder()
//                .addModifiers(Modifier.PUBLIC)
//                .addStatement("routeMap = new $T<>()", HashMap.class);
//        for (String key : nameMap.keySet()) {
//            String name = nameMap.get(key);
//            constructorBuidler.addStatement("routeMap.put(\"$N\", \"$N\")", key, name);
//        }
//        MethodSpec constructorName = constructorBuidler.build();
//
//        //generate getActivityRouteName method
//        MethodSpec routeName = MethodSpec.methodBuilder("getActivityName")
//                .addModifiers(Modifier.PUBLIC)
//                .returns(String.class)
//                .addParameter(String.class, "routeName")
//                .beginControlFlow("if (null != routeMap && !routeMap.isEmpty())")
//                .addStatement("return (String)routeMap.get(routeName)")
//                .endControlFlow()
//                .addStatement("return \"\"")
//                .build();
//
//        //generate class
//        TypeSpec typeSpec = TypeSpec.classBuilder("AnnotationRoute$Finder")
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(constructorName)
//                .addMethod(routeName)
//                .addSuperinterface(Provider.class)
//                .addField(HashMap.class, "routeMap", Modifier.PRIVATE)
//                .build();
//
//
//        JavaFile javaFile = JavaFile.builder("com.example.juexingzhe.annotaioncompiletest", typeSpec).build();
//        try {
//            javaFile.writeTo(mFiler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
