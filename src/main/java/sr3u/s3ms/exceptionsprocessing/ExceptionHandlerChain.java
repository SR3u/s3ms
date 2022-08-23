package sr3u.s3ms.exceptionsprocessing;

import sr3u.s3ms.S3mRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHandlerChain implements ExceptionHandler<Throwable> {
    private List<ExceptionHandler<? extends Throwable>> exceptionHandlers = new ArrayList<>();

    public void addHandler(ExceptionHandler<? extends Throwable> e) {
        exceptionHandlers.add(e);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(Throwable t) {
        for (ExceptionHandler e : exceptionHandlers) {
            if (e.canHandle(t)) {
                e.handle(t);
                if (e.isTerminal()) {
                    return;
                }
            }
        }
        throw new S3mRuntimeException(t);
    }

}
