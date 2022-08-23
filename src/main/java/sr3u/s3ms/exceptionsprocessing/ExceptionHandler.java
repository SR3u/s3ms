package sr3u.s3ms.exceptionsprocessing;

public interface ExceptionHandler<T extends Throwable> {
    void handle(T exception);

    default boolean isTerminal() {
        return true;
    }

    default boolean canHandle(Throwable t) {
        try {
            @SuppressWarnings("unchecked") T dummy = (T) t;
        } catch (ClassCastException ignored) {
            return false;
        }
        return true;
    }
}
