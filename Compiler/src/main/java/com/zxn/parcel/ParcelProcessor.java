package com.zxn.parcel;

import com.zxn.parcel.annotation.Parcelable;

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
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @AutoService(Processor.class) 这个注解不要忘了，否则无法生成Java文件
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.zxn.parcel.annotation.Parcelable")
public class ParcelProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
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
                    ProxyInfo pi = new ProxyInfo(enclosingElement
                            .getQualifiedName().toString(), enclosingElement.getEnclosedElements());

                    writeLog(pi.getFullName());
                    JavaFileObject jfo = filer.createSourceFile(
                            pi.getFullName(), enclosingElement);
                    Writer writer = jfo.openWriter();
                    writeLog(pi.createCode());
                    writer.write(pi.createCode());
                    writer.flush();
                    writer.close();
                    writeLog("ok");
                } catch (Exception e) {
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
}