// ITokenAidlInterface.aidl
package com.example.aidldemo;

// Declare any non-default types here with import statements

interface ITokenAidlInterface {
    //同步登录token到服务端APP的方法
    void postLoginToken(in String accessToken);
}