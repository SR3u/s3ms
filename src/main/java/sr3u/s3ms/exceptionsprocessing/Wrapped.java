package sr3u.s3ms.exceptionsprocessing;

import java.util.Objects;

public class Wrapped<R> {
    private final ExceptionHandlerChain exceptionHandler;
    private final R value;

    public Wrapped(ExceptionHandlerChain left, R right) {
        this.exceptionHandler = left;
        this.value = right;
    }

    public static <R> Wrapped<R> of(ExceptionHandlerChain left, R right) {
        return new Wrapped<>(left, right);
    }

    public ExceptionHandlerChain getExceptionHandler() {
        return exceptionHandler;
    }

    public R getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wrapped<?> wrapped = (Wrapped<?>) o;
        return Objects.equals(exceptionHandler, wrapped.exceptionHandler) && Objects.equals(value, wrapped.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exceptionHandler, value);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + exceptionHandler +
                ", right=" + value +
                '}';
    }
}
