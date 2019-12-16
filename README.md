auto_parcel  是Android序列化工具,极其简单。

[![](https://jitpack.io/v/bug2048/auto_parcel.svg)](https://jitpack.io/#bug2048/auto_parcel)

## 导入依赖
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
## 如何使用
```groovy

@com.zxn.parcel.Parcelable
public class People {

    private int age;
    private String name;
    private long birthday;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}

//测试
    public void test(View view) {
        People people = new People();
        people.setAge(18);
        people.setBirthday(System.currentTimeMillis());
        people.setName("小明");
        Log.i("People",
                String.format("name = %1s,age = %2s,birthday = %3s", people.getName(), String.valueOf(people.getAge()), String.valueOf(people.getBirthday())));

        Intent intent = new Intent();
        intent.putExtra("data", People$$Parcelable.toParcelable(people));

        People people2 = intent.getParcelableExtra("data");

        Log.i("People2",
                String.format("name = %1s,age = %2s,birthday = %3s", people2.getName(), String.valueOf(people2.getAge()), String.valueOf(people2.getBirthday())));
    }
```


## 注意事项
### 1、目前不支持内部类使用
### 2、须保留成员变量的get\set方法


## License
```groovy
   Copyright 2019 Author Liuliqiang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```   
