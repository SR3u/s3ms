package sr3u.functionalex.primitive;

import sr3u.functionalex.Functionex;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * long-valued result.  This is the {@code int}-to-{@code long} primitive
 * specialization for {@link Functionex}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #applyAsLong(int)}.
 *
 * @see Functionex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface IntToLongFunctionex {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @SuppressWarnings("RedundantThrows")
    long applyAsLong(int value) throws Exception;
}
