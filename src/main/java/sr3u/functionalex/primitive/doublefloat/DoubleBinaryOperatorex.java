package sr3u.functionalex.primitive.doublefloat;

import sr3u.functionalex.BinaryOperatorex;

/**
 * Represents an operation upon two {@code double}-valued operands and producing an
 * {@code double}-valued result.   This is the primitive type specialization of
 * {@link BinaryOperatorex} for {@code double}.
 *
 * <p>This is a {@link FunctionalInterface}
 * whose functional method is {@link #applyAsDouble(double, double)}.
 *
 * @see BinaryOperatorex
 * @see DoubleUnaryOperatorex
 * @since 1.8.0.0
 */
@FunctionalInterface
public interface DoubleBinaryOperatorex {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    @SuppressWarnings("RedundantThrows")
    double applyAsDouble(double left, double right) throws Exception;
}
