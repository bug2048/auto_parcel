auto_parcel is an Android serialization tool that is extremely simple.

[! [] (https://jitpack.io/v/bug2048/auto_parcel.svg)] (https://jitpack.io/#bug2048/auto_parcel)
[! [Download] (https://api.bintray.com/packages/lqos/maven/auto_parcel/images/download.svg?version=0.1.0)] (https://bintray.com/lqos/maven/ auto_parcel / 0.1.0 / link)

## Import dependencies
`` `groovy
dependencies {
    implementation 'com.zxn.parcel: auto_parcel: 0.1.0'
    annotationProcessor 'com.zxn.parcel: auto_parcel: 0.1.0'
}
`` `
### or
`` `groovy
allprojects {
    // All items are quoted
    repositories {
        maven {url 'https://jitpack.io'}
        ....
    }
}
dependencies {
    implementation 'com.github.bug2048: auto_parcel: 0.1.0'
    annotationProcessor 'com.github.bug2048: auto_parcel: 0.1.0'
}
`` `

## how to use
### Naming rules
#### 1. The use of non-inner classes is the class name + $ Parcelable, that is: the class name $ Parcelable
#### 2. The inner class uses the parent class name + class name + $ Parcelable, that is: the parent class name class name $ Parcelable
#### 3. The inner class of the inner class is the name of the parent and parent class + parent class name + class name + $ Parcelable, that is: parent parent class name parent class name class name $ Parcelable. . .

`` `groovy

@ com.zxn.parcel.Parcelable
public class People {

    private int age;
    private String name;
    private long birthday;

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public long getBirthday () {
        return birthday;
    }

    public void setBirthday (long birthday) {
        this.birthday = birthday;
    }
}

//test
    public void test (View view) {
        People people = new People ();
        people.setAge (18);
        people.setBirthday (System.currentTimeMillis ());
        people.setName ("小 明");
        Log.i ("People",
                String.format ("name =% 1s, age =% 2s, birthday =% 3s", people.getName (), String.valueOf (people.getAge ()), String.valueOf (people.getBirthday ()))) ;

        Intent intent = new Intent ();
        intent.putExtra ("data", People $ Parcelable.toParcelable (people));

        People people2 = intent.getParcelableExtra ("data");

        Log.i ("People2",
                String.format ("name =% 1s, age =% 2s, birthday =% 3s", people2.getName (), String.valueOf (people2.getAge ()), String.valueOf (people2.getBirthday ()))) ;
    }
`` `


## Precautions
### 1. The get / set method must be retained

## Release Notes
### v0.1.0: Support internal class serialization
### v0.5.0: Format code to fix known issues
### v0.1.0: Implement automatic serialization

## License
`` `groovy
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
`` `
