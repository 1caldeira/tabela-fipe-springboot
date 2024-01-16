package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModelData(@JsonAlias("Valor") String value,
                        @JsonAlias("Marca") String brandName,
                        @JsonAlias("Modelo") String model,
                        @JsonAlias("AnoModelo") Integer year,
                        @JsonAlias("Combustivel") String fuelType) {
}
