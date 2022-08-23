package sr3u.s3ms.exceptionsprocessing;

import java.util.function.Function;
import java.util.function.Supplier;

public class Op<T> {

    private final Supplier<T> wrapped;
    private final ExceptionHandlerChain exceptionHandlerChain;
    private final Runnable op;

    public Op(Supplier<T> value, ExceptionHandlerChain exceptionHandlerChain) {
        this(value, exceptionHandlerChain, null);
    }

    public Op(Supplier<T> value, ExceptionHandlerChain exceptionHandlerChain, Runnable op) {
        this.exceptionHandlerChain = exceptionHandlerChain;
        this.wrapped = value;
        this.op = op;
    }

    public Op(T value, ExceptionHandlerChain exceptionHandlerChain) {
        this(() -> value, exceptionHandlerChain, null);
    }

    public Op(T value, ExceptionHandlerChain exceptionHandlerChain, Runnable op) {
        this(() -> value, exceptionHandlerChain, op);
    }

    public Op<T> print() {
        exceptionHandlerChain.addHandler(System.out::println);
        return this;
    }

    public Op<T> throwRuntime(Function<Throwable, RuntimeException> runtimeExceptionCreator) {
        exceptionHandlerChain.addHandler(runtimeExceptionCreator::apply);
        return this;
    }

    public T rethrow(Function<Throwable, RuntimeException> runtimeExceptionCreator) {
        return throwRuntime(runtimeExceptionCreator).rethrow();
    }

    public T rethrow() {
        return terminate();
    }

    public T terminate() {
        if (op != null) {
            op.run();
        }
        return wrapped.get();
    }
}
