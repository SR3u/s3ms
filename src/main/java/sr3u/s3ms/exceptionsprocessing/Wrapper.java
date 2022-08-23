package sr3u.s3ms.exceptionsprocessing;

import sr3u.functionalex.Consumerex;
import sr3u.functionalex.Functionex;
import sr3u.functionalex.Predicatex;
import sr3u.functionalex.Supplierex;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Wrapper {
    public static <T> Wrapped<Consumer<? super T>> wrapConsumer(Consumerex<? super T> consumer) {
        ExceptionHandlerChain exceptionHandlerChain = new ExceptionHandlerChain();
        Consumer<T> wrappedConsumer = (v) -> {
            try {
                consumer.accept(v);
            } catch (Exception e) {
                exceptionHandlerChain.handle(e);
            }
        };
        return Wrapped.of(exceptionHandlerChain, wrappedConsumer);
    }

    public static <T> Wrapped<Predicate<? super T>> wrapPredicate(Predicatex<? super T> predicate) {
        ExceptionHandlerChain exceptionHandlerChain = new ExceptionHandlerChain();
        Predicate<T> wrappedPredicate = (v) -> {
            try {
                return predicate.test(v);
            } catch (Exception e) {
                exceptionHandlerChain.handle(e);
            }
            return false;
        };
        return Wrapped.of(exceptionHandlerChain, wrappedPredicate);
    }


    public static <U, T> Wrapped<Function<? super T, ? extends U>> wrapFunction(Functionex<? super T, ? extends U> function) {
        ExceptionHandlerChain exceptionHandlerChain = new ExceptionHandlerChain();
        Function<T, U> wrappedFunction = (v) -> {
            try {
                return function.apply(v);
            } catch (Exception e) {
                exceptionHandlerChain.handle(e);
            }
            return null;
        };
        return Wrapped.of(exceptionHandlerChain, wrappedFunction);
    }

    public static <U, T> Wrapped<Function<? super T, Optional<U>>> wrapOptionalFunction(Functionex<? super T, Optional<U>> function) {
        ExceptionHandlerChain exceptionHandlerChain = new ExceptionHandlerChain();
        Function<T, Optional<U>> wrappedFunction = (v) -> {
            try {
                return function.apply(v);
            } catch (Exception e) {
                exceptionHandlerChain.handle(e);
            }
            return Optional.empty();
        };
        return Wrapped.of(exceptionHandlerChain, wrappedFunction);
    }

    public static <T> Wrapped<Supplier<T>> wrapSupplier(Supplierex<? extends T> other) {
        ExceptionHandlerChain exceptionHandlerChain = new ExceptionHandlerChain();
        Supplier<T> wrappedSupplier = () -> {
            try {
                return other.get();
            } catch (Exception e) {
                exceptionHandlerChain.handle(e);
            }
            return null;
        };
        return Wrapped.of(exceptionHandlerChain, wrappedSupplier);
    }
}

