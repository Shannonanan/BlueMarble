package co.za.bluemarble.common.executor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link za.co.bluemarble.common.UseCase} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}