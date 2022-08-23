package sr3u.s3ms.optionals;

import sr3u.functionalex.Consumerex;
import sr3u.functionalex.Functionex;
import sr3u.functionalex.Predicatex;
import sr3u.functionalex.Supplierex;
import sr3u.s3ms.exceptionsprocessing.Op;
import sr3u.s3ms.exceptionsprocessing.Wrapped;
import sr3u.s3ms.exceptionsprocessing.Wrapper;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An exceptions-friendly wrapper for {@link Optional}
 *
 * @since 1.8.0.0
 */

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Optionalex<T> {
    private final Optional<T> optional;

    /**
     * Constructs an instance with the value present.
     *
     * @param optional the internal {@code Optional}
     * @throws NullPointerException if value is null
     */
    private Optionalex(Optional<T> optional) {
        this.optional = optional;
    }

    /**
     * Returns an empty {@code Optionalex} instance.  No value is present for this
     * Optional.
     *
     * @param <T> Type of the non-existent value
     * @return an empty {@code Optionalex}
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     */
    public static <T> Optionalex<T> empty() {
        return new Optionalex<>(Optional.empty());
    }

    /**
     * Returns an {@code Optionalex} wrapper of the specified {@code Optional}.
     *
     * @param <T>      the class of the value of the optional
     * @param optional the optional to be wrapped
     * @return an {@code Optionalex} that wraps the supplied optional
     */
    private static <T> Optionalex<T> ofOptional(Optional<T> optional) {
        return new Optionalex<>(optional);
    }

    /**
     * Returns an {@code Optionalex} with the specified present non-null value.
     *
     * @param <T>   the class of the value
     * @param value the value to be present, which must be non-null
     * @return an {@code Optionalex} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T> Optionalex<T> of(T value) {
        return new Optionalex<>(Optional.of(value));
    }

    /**
     * Returns an {@code Optionalex} describing the specified value, if non-null,
     * otherwise returns an empty {@code Optionalex}.
     *
     * @param <T>   the class of the value
     * @param value the possibly-null value to describe
     * @return an {@code Optionalex} with a present value if the specified value
     * is non-null, otherwise an empty {@code Optionalex}
     */
    public static <T> Optionalex<T> ofNullable(T value) {
        return new Optionalex<>(Optional.ofNullable(value));
    }

    /**
     * If a value is present in this {@code Optionalex}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code Optionalex}
     * @throws NoSuchElementException if there is no value present
     * @see Optional#isPresent()
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public T get() {
        return optional.get();
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return optional.isPresent();
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        optional.ifPresent(consumer);
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public Op<Optionalex<T>> ifPresentex(Consumerex<? super T> consumer) {
        Wrapped<Consumer<? super T>> wrapped = Wrapper.wrapConsumer(consumer);
        Runnable op = () -> optional.ifPresent(wrapped.getValue());
        return new Op<>(this, wrapped.getExceptionHandler(), op);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an {@code Optionalex} describing the value, otherwise return an
     * empty {@code Optionalex}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code Optionalex} describing the value of this {@code Optionalex}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the predicate is null
     */
    public Optionalex<T> filter(Predicate<? super T> predicate) {
        return Optionalex.ofOptional(optional.filter(predicate));
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an {@code Optionalex} describing the value, otherwise return an
     * empty {@code Optionalex}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code Optionalex} describing the value of this {@code Optionalex}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the predicate is null
     */
    public Op<Optionalex<T>> filterex(Predicatex<? super T> predicate) {
        Wrapped<Predicate<? super T>> wrapped = Wrapper.wrapPredicate(predicate);
        return new Op<>(() -> ofOptional(optional.filter(wrapped.getValue())), wrapped.getExceptionHandler());
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optionalex} describing the
     * result.  Otherwise return an empty {@code Optionalex}.
     *
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code Optionalex} describing the result of applying a mapping
     * function to the value of this {@code Optionalex}, if a value is present,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the mapping function is null
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of file names, selects one that has
     * not yet been processed, and then opens that file, returning an
     * {@code Optionalex<FileInputStream>}:
     *
     * <pre>{@code
     *     Optional<FileInputStream> fis =
     *         names.stream().filter(name -> !isProcessedYet(name))
     *                       .findFirst()
     *                       .map(name -> new FileInputStream(name));
     * }</pre>
     * <p>
     * Here, {@code findFirst} returns an {@code Optionalex<String>}, and then
     * {@code map} returns an {@code Optionalex<FileInputStream>} for the desired
     * file if one exists.
     */
    public <U> Optionalex<U> map(Function<? super T, ? extends U> mapper) {
        return new Optionalex<>(optional.map(mapper));
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optionalex} describing the
     * result.  Otherwise return an empty {@code Optionalex}.
     *
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code Optionalex} describing the result of applying a mapping
     * function to the value of this {@code Optionalex}, if a value is present,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the mapping function is null
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of file names, selects one that has
     * not yet been processed, and then opens that file, returning an
     * {@code Optionalex<FileInputStream>}:
     *
     * <pre>{@code
     *     Optional<FileInputStream> fis =
     *         names.stream().filter(name -> !isProcessedYet(name))
     *                       .findFirst()
     *                       .map(name -> new FileInputStream(name));
     * }</pre>
     * <p>
     * Here, {@code findFirst} returns an {@code Optionalex<String>}, and then
     * {@code map} returns an {@code Optionalex<FileInputStream>} for the desired
     * file if one exists.
     */
    public <U> Op<Optionalex<U>> mapex(Functionex<? super T, ? extends U> mapper) {
        Wrapped<Function<? super T, ? extends U>> functionWrapped = Wrapper.wrapFunction(mapper);
        return new Op<>(() -> Optionalex.ofOptional(optional.map(functionWrapped.getValue())), functionWrapped.getExceptionHandler());
    }

    /**
     * If a value is present, apply the provided {@code Optionalex}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optionalex}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optionalex},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optionalex}.
     *
     * @param mapper a mapping function to apply to the value, if present
     *               the mapping function
     * @return the result of applying an {@code Optionalex}-bearing mapping
     * function to the value of this {@code Optionalex}, if a value is present,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the mapping function is null or returns
     *                              a null result
     */
    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        return optional.flatMap(mapper);
    }

    /**
     * If a value is present, apply the provided {@code Optionalex}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optionalex}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optionalex},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optionalex}.
     *
     * @param mapper a mapping function to apply to the value, if present
     *               the mapping function
     * @return the result of applying an {@code Optionalex}-bearing mapping
     * function to the value of this {@code Optionalex}, if a value is present,
     * otherwise an empty {@code Optionalex}
     * @throws NullPointerException if the mapping function is null or returns
     *                              a null result
     */
    public <U> Op<Optionalex<U>> flatMapex(Functionex<? super T, Optional<U>> mapper) {
        Wrapped<Function<? super T, Optional<U>>> functionWrapped = Wrapper.wrapOptionalFunction(mapper);
        return new Op<>(() -> Optionalex.ofOptional(optional.flatMap(functionWrapped.getValue())),
                functionWrapped.getExceptionHandler());
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present, may
     *              be null
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return optional.orElse(other);
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value
     *              is present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     *                              null
     */
    public T orElseGet(Supplier<? extends T> other) {
        return optional.orElseGet(other);
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value
     *              is present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     *                              null
     */
    public Op<Optional<T>> orElseGetex(Supplierex<? extends T> other) {
        Wrapped<? extends Supplier<? extends T>> supplierWrapped = Wrapper.wrapSupplier(other);
        return new Op<>(() -> Optional.ofNullable(optional.orElseGet(supplierWrapped.getValue())),
                supplierWrapped.getExceptionHandler());
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @param exceptionSupplier The supplier which will return the exception to
     *                          be thrown
     * @return the present value
     * @throws X                    if there is no value present
     * @throws NullPointerException if no value is present and
     *                              {@code exceptionSupplier} is null
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return optional.orElseThrow(exceptionSupplier);
    }

    /**
     * A shortcut for {@code filter(Objects::nonNull)}
     */
    public Optionalex<T> nonNull() {
        return filter(Objects::nonNull);
    }

}
