package com.project.cryptoapp.model;

public class EncryptionResult {
    private final boolean success;
    private final long timeMillis;
    private final String message;
    private final String outputPath;

    public EncryptionResult(boolean success, long timeMillis, String message, String outputPath) {
        this.success = success;
        this.timeMillis = timeMillis;
        this.message = message;
        this.outputPath = outputPath;
    }

    public boolean isSuccess() { return success; }
    public long getTimeMillis() { return timeMillis; }
    public String getMessage() { return message; }
    public String getOutputPath() { return outputPath; }
}

