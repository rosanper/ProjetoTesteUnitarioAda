package br.com.ada.testeautomatizado.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response <T> {
    String message;
    T detail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return Objects.equals(message, response.message) && Objects.equals(detail, response.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, detail);
    }
}
