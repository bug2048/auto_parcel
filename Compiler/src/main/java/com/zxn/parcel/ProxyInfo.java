package com.zxn.parcel;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

/**
 * @author liuliqiang
 * @date 2019-12-12
 */
public class ProxyInfo {
    private static final String SUFFIX = "$Parcelable";

    private String packageName;
    private String proxyName;
    private String className;
    private List<? extends Element> mElements;

    public ProxyInfo(List<? extends Element> enclosedElements) {
        mElements = enclosedElements;
    }

    public void setClassName(String className) {
        this.className = className;
        if (this.className.startsWith(packageName)) {
            this.className = this.className.replace(packageName + ".", "");
        }
        proxyName = className.replace(packageName + ".", "").replace(".", "") + SUFFIX;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String createCode() {
        return getcode();
//        return packageName + proxyName + className;
    }

    public String getFullName() {
        return packageName + "." + proxyName;
    }

    private String getcode() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("package ");
        buffer.append(packageName);
        buffer.append(";");
        buffer.append("\n\n");
        buffer.append("import android.os.Parcel;\n");
        buffer.append("import android.os.Parcelable;\n");
        buffer.append("\n");
        buffer.append("public class ");
        buffer.append(proxyName);
        buffer.append(" extends ");
        buffer.append(className);
        buffer.append(" implements Parcelable");
        buffer.append(" {\n");
        buffer.append("\n    public ");
        buffer.append(proxyName);
        buffer.append("(){}\n");
        buffer.append("\n    protected ");
        buffer.append(proxyName);
        buffer.append("(Parcel parcel){\n");
//        buffer.append("        super(parcel);\n");
        try {
            if (mElements != null) {
                for (Element element : mElements) {
                    if (element.getKind().equals(ElementKind.FIELD) &&
                            (!element.getModifiers().contains(Modifier.FINAL) && !element.getModifiers().contains(Modifier.STATIC))) {
                        TypeMirror type = element.asType();
                        buffer.append(readFild("parcel", element.getSimpleName().toString(), type.toString()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            buffer.append("\n/**Exception\n");
            buffer.append(e);
            buffer.append("\n**/\n");
        }
        //读参数
        buffer.append("    }\n\n");
        buffer.append("    @Override\n");
        buffer.append("    public void writeToParcel(Parcel dest, int flags) {\n");
        //写参参数

        try {
            if (mElements != null) {
                for (Element element : mElements) {
                    if (element.getKind().equals(ElementKind.FIELD) &&
                            (!element.getModifiers().contains(Modifier.FINAL) && !element.getModifiers().contains(Modifier.STATIC))) {
                        TypeMirror type = element.asType();
                        buffer.append(writeFilde("dest", element.getSimpleName().toString(), type.toString()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            buffer.append("\n/**Exception\n");
            buffer.append(e);
            buffer.append("\n**/\n");
        }
        buffer.append("\n    }\n");
        buffer.append("    @Override\n" +
                "    public int describeContents() {\n" +
                "        return 0;\n" +
                "    }\n\n");
        buffer.append("    public static final Creator<").append(proxyName).append("> CREATOR = new Creator<").append(proxyName).append(">() {\n").append("        @Override\n").append("        public ").append(proxyName).append(" createFromParcel(Parcel in) {\n").append("            return new ").append(proxyName).append("(in);\n").append("        }\n\n").append("        @Override\n").append("        public ").append(proxyName).append("[] newArray(int size) {\n").append("            return new ").append(proxyName).append("[size];\n").append("        }\n").append("    };\n\n");
        buffer.append("    public static final ")
                .append(proxyName).
                append(" toParcelable(")
                .append(className)
                .append(" bean){\n        ").append(proxyName).append(" parcelable = new ").append(proxyName).append("();\n");

        try {
            if (mElements != null) {
                for (Element element : mElements) {
                    if (element.getKind().equals(ElementKind.FIELD) &&
                            (!element.getModifiers().contains(Modifier.FINAL) && !element.getModifiers().contains(Modifier.STATIC))) {
                        TypeMirror type = element.asType();
                        buffer.append(writeFilde(element.getSimpleName().toString(), type));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            buffer.append("\n/**Exception\n");
            buffer.append(e);
            buffer.append("\n**/\n");
        }
        buffer.append("       return parcelable;\n")
                .append("}\n");
        buffer.append("}\n");
        return buffer.toString();
    }

    private String reNameFild(String simpleName) {
        if (simpleName.length() == 1) {
            return simpleName.toUpperCase();
        }
        if (simpleName.startsWith("m") && (simpleName.charAt(1) >= 'A' && simpleName.charAt(1) <= 'Z')) {
            simpleName = simpleName.substring(1);
        } else {
            simpleName = simpleName.substring(0, 1).toUpperCase() + simpleName.substring(1);
        }
        return simpleName;
    }

    private String writeFilde(String simpleName, TypeMirror type) {
        simpleName = reNameFild(simpleName);
        if ("boolean".equals(type.toString())) {
            return "        parcelable.set" + simpleName + "(bean.is" + simpleName + "());\n";
        } else {
            return "        parcelable.set" + simpleName + "(bean.get" + simpleName + "());\n";
        }
    }

    private String writeFilde(String parcel, String simpleName, String type) {
        simpleName = reNameFild(simpleName);
        if (type.equals("boolean")) {
            return "        " + parcel + ".writeValue(is" + simpleName + "()" + ");\n";
        } else {
            return "        " + parcel + ".writeValue(get" + simpleName + "()" + ");\n";
        }
    }

    private String readFild(String parcel, String simpleName, String type) {
        simpleName = reNameFild(simpleName);
        return "        set" + simpleName + "((" + type + ") " + parcel + ".readValue(this.getClass().getClassLoader())" + ");\n";
    }

}
