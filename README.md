auto_parcel  是Android序列化工具,极其简单。

[![](https://jitpack.io/v/bug2048/auto_parcel.svg)](https://jitpack.io/#bug2048/auto_parcel)

如何导入auto_parcel依赖
gradle 
```groovy
allprojects {
    //所有的项目都引用
    repositories {
        maven { url 'https://jitpack.io' }
        ....
    }
}

dependencies {
    implementation 'com.github.bug2048:auto_parcel:0.0.5'
    annotationProcessor 'com.github.bug2048:auto_parcel:0.0.5'
}
```


