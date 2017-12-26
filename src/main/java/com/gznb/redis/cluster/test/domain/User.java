package com.gznb.redis.cluster.test.domain;

import java.io.Serializable;

/**
 * @author jiangjun
 * @create 2017/9/19
 */
public class User implements Serializable {

    private String name;

    private String password;

    private Integer age;

    public User(String name, String password, Integer age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
