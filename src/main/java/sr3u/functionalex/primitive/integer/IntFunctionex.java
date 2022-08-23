package sr3u.functionalex.primitive.integer;

import sr3u.functionalex.Functionex;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * result.  This is the {@code int}-consuming primitive specialization for
 * {@link Functionex}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #apply(int)}.
 *
 * @param <R> the type of the result of the function
 * @see Functionex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface IntFunctionex<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @SuppressWarnings("RedundantThrows")
    R apply(int value) throws Exception;
}
