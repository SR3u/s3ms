package sr3u.functionalex.primitive.longinteger;


import sr3u.functionalex.Functionex;

/**
 * Represents a function that produces a long-valued result.  This is the
 * {@code long}-producing primitive specialization for {@link Functionex}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #applyAsLong(Object)}.
 *
 * @param <T> the type of the input to the function
 * @see Functionex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface ToLongFunctionex<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @SuppressWarnings("RedundantThrows")
    long applyAsLong(T value) throws Exception;

}
