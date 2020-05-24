package com.autoplag.config.constant;

import com.autoplag.util.ResourceManager;

public final class Detection {
    public static final int DEFAULT_POOL_SIZE = ResourceManager.DETECTION.getPropertyAsInt("default.pool.size");
    public static final int EXECUTION_TIMEOUT_IN_MINUTES = ResourceManager.DETECTION.getPropertyAsInt("execution.timeout.inminutes");
    public static final int TASK_CANCEL_RESPONSE_TIMEOUT_IN_SECONDS = ResourceManager.DETECTION.getPropertyAsInt("task.cancel.response.timeout.inseconds");

    private Detection() {
    }
}
