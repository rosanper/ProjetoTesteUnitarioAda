package br.com.ada.testeautomatizado.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoDTO {

    private String placa;
    private String modelo;
    private String marca;
    private Boolean disponivel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFabricacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VeiculoDTO that = (VeiculoDTO) o;
        return Objects.equals(placa, that.placa) && Objects.equals(modelo, that.modelo) && Objects.equals(marca, that.marca) && Objects.equals(disponivel, that.disponivel) && Objects.equals(dataFabricacao, that.dataFabricacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, modelo, marca, disponivel, dataFabricacao);
    }
}
