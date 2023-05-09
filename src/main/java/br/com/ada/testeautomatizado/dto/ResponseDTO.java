package br.com.ada.testeautomatizado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO<T> {
    String message;
    T detail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO<?> responseDTO = (ResponseDTO<?>) o;
        return Objects.equals(message, responseDTO.message) && Objects.equals(detail, responseDTO.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, detail);
    }
}
