package com.example.myapplication;

import com.zxn.parcel.annotation.Parcelable;

/**
 * TODO:describe what the class or interface does.
 *
 * @author liuliqiang
 * @date 2019-12-15
 */
@Parcelable
public class MyBean {
    private String name;
    private int obj;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }

    @Parcelable
    public static class MyBean2 {
        private String name;
        private int obj;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getObj() {
            return obj;
        }

        public void setObj(int obj) {
            this.obj = obj;
        }

        @Parcelable
        public static class MyBean3 {
            private String name;
            private int obj;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public int getObj() {
                return obj;
            }

            public void setObj(int obj) {
                this.obj = obj;
            }
        }
    }
}
