package com.zxn.parcel;

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
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.zxn.parcel.Parcelable")
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

//
//    "package com.example.parcelabledemo;\n"+
//            "\n"+
//            "import android.os.Parcel;\n"+
//            "\n"+
//            "/**\n"+
//            " *\n"+
//            " */\n"+
//            "public class PdUtils {\n"+
//            "    public static void writeValue(Parcel parcel, Object value) {\n"+
//            "        parcel.writeValue(value);\n"+
//            "    }\n"+
//            "\n"+
//            "    public static Object readValue(Parcel parcel, Object value) {\n"+
//            "        return parcel.readValue(value.getClass().getClassLoader());\n"+
//            "    }\n"+
//            "}\n"

    private void writeLog(String str) {
        // try {
        // FileWriter fw = new FileWriter(new File("D:/process.txt"), true);
        // fw.write(str + "\n");
        // fw.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }
}