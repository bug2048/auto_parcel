package com.zxn.parcel;

import com.zxn.parcel.annotation.Parcelable;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 */
//@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.zxn.parcel.annotation.Parcelable")
public class ParcelProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
        elementUtils = env.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        try {
            Set<? extends Element> set = roundEnv
                    .getElementsAnnotatedWith(Parcelable.class);
            for (Element element : set) {
                try {
                    TypeElement enclosingElement = (TypeElement) element;
                    if (enclosingElement.getModifiers().contains(Modifier.PRIVATE) || enclosingElement.getModifiers().contains(Modifier.FINAL)) {
                        error(element, "修饰符不能是'private'和'final'");
                    }
                    ProxyInfo pi = new ProxyInfo(enclosingElement.getEnclosedElements());
                    pi.setPackageName(getPackageName(enclosingElement));
                    pi.setClassName(enclosingElement.getQualifiedName().toString());
                    writeLog(pi.getFullName());
                    JavaFileObject jfo = filer.createSourceFile(
                            pi.getFullName(), enclosingElement);
                    Writer writer = jfo.openWriter();
                    writeLog(pi.createCode());
                    writer.write(pi.createCode());
                    writer.flush();
                    writer.close();
                    writeLog("ok");
                } catch (IOException e) {
                    e.printStackTrace();
                    writeLog(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeLog(e.getMessage());
        }
        return true;
    }

    private void writeLog(String str) {
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(ERROR, message, element);
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
}