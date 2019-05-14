package com.pc.mimi.webSocket;

public interface ICallback<T> {

    void onSuccess(T t);

    void onFail(String msg);

}