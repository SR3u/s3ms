package sr3u.functionalex.primitive;


import sr3u.functionalex.Functionex;

/**
 * Represents a function that accepts a double-valued argument and produces an
 * int-valued result.  This is the {@code double}-to-{@code int} primitive
 * specialization for {@link Functionex}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #applyAsInt(double)}.
 *
 * @see Functionex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface DoubleToIntFunctionex {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @SuppressWarnings("RedundantThrows")
    int applyAsInt(double value) throws Exception;
}
