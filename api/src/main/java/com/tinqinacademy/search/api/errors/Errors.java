package com.tinqinacademy.search.api.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class Errors {
    public static class ErrorsBuilder {
        private final List<ErrorInfo> errorInfos;

        public ErrorsBuilder() {
            errorInfos = new ArrayList<>();
        }

        public ErrorsBuilder error(String message, HttpStatus status) {
            errorInfos.add(
                    ErrorInfo.builder()
                            .error(message)
                            .status(status)
                            .build()
            );

            return this;
        }

        public Errors build() {
            return new Errors(errorInfos);
        }
    }

    private final List<ErrorInfo> errorInfos;

    public void addError(ErrorInfo errorInfo) {
        errorInfos.add(errorInfo);
    }

    public HttpStatus getStatus() {
        return errorInfos.getFirst().getStatus();
    }

    public static ErrorsBuilder builder() {
        return new ErrorsBuilder();
    }
}
