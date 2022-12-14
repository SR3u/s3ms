package sr3u.functionalex.primitive.longinteger;

import sr3u.functionalex.BinaryOperatorex;

/**
 * Represents an operation upon two {@code long}-valued operands and producing an
 * {@code long}-valued result.   This is the primitive type specialization of
 * {@link BinaryOperatorex} for {@code long}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #applyAsLong(long, long)}.
 *
 * @see BinaryOperatorex
 * @see LongUnaryOperatorex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface LongBinaryOperatorex {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    @SuppressWarnings("RedundantThrows")
    long applyAsLong(long left, long right) throws Exception;
}
