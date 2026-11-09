package mx.uv.tcsw.adapter.web.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String timestamp;
    private final int status;
    private final String code;
    private final String message;
    private final String path;
    private final List<Detalle> details;

    public ErrorResponse(String timestamp, int status, String code, String message,
                         String path, List<Detalle> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    public String getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public List<Detalle> getDetails() { return details; }

    public static class Detalle {
        private final String field;
        private final String message;

        public Detalle(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() { return field; }
        public String getMessage() { return message; }
    }
}