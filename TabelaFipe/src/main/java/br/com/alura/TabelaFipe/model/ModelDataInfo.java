package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModelDataInfo(@JsonAlias("codigo") Integer code,
                            @JsonAlias("nome") String modelName) {
}
