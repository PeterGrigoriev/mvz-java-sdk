package com.movilizer.push;

public interface IMovilizerPushCallListener {
    IMovilizerPushCallListener VOID = new IMovilizerPushCallListener() {
        @Override
        public void onSuccess() {
        }

        @Override
        public void onFailure() {
        }
    };

    void onSuccess();
    void onFailure();
}
