package io.mstream.addresses.api;

public abstract class LightweightException extends RuntimeException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
