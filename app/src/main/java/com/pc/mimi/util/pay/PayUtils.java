package com.pc.mimi.util.pay;

public interface PayUtils {
    void toPay();

    interface result {
        void onSuccess();
        void onFail();
    }
}
