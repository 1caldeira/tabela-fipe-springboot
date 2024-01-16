package br.com.alura.TabelaFipe.service;

import java.util.List;

public interface DataConversionInterface {
    <T> T getData(String json, Class<T> tClass);
    <T> List<T> getDataList(String json, Class<T> elementType);
}
