package pl.semantyk.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class SystemRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public static SystemRuntimeException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemRuntimeException) {
            SystemRuntimeException se = (SystemRuntimeException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new SystemRuntimeException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new SystemRuntimeException(exception.getMessage(), exception, errorCode);
        }
    }

    public static SystemRuntimeException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    private ErrorCode errorCode;
    private final Map<String, Object> properties = new TreeMap<>();

    public SystemRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SystemRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemRuntimeException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SystemRuntimeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public SystemRuntimeException setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) properties.get(name);
    }

    public SystemRuntimeException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (errorCode != null) {
                s.println("\t" + errorCode + ":" + errorCode.getClass().getName());
            }
            for (String key : properties.keySet()) {
                s.println("\t" + key + "=[" + properties.get(key) + "]");
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (StackTraceElement aTrace : trace) s.println("\tat " + aTrace);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }

}
