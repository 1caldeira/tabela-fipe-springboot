package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.*;
import br.com.alura.TabelaFipe.service.RequestAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    DataConversion conversion = new DataConversion();
    private RequestAPI requestAPI = new RequestAPI();
    private final String ADDRESS = "https://parallelum.com.br/fipe/api/v1/";

    public void showMenu() {
        System.out.println("Bem-vindo ao sistema de pesquisa da tabela FIPE");
        System.out.println("Deseja pesquisar por 'carros', 'motos' ou 'caminhoes?'");
        Scanner sc = new Scanner(System.in);
        String vehicleType = sc.nextLine();

        var json = requestAPI.getData(ADDRESS + vehicleType + "/marcas");
        List<VehicleTypeData> vehicleTypeDataList = conversion.getDataList(json, VehicleTypeData.class);
        vehicleTypeDataList.forEach(v -> System.out.println("Marca: " + v.brand() + " ---  Codigo: " + v.code()));

        System.out.println("Digite o codigo da marca que deseja pesquisar: ");
        int brandCode = sc.nextInt();
        json = requestAPI.getData(ADDRESS + vehicleType + "/marcas/" + brandCode + "/modelos");
        BrandData brandData = conversion.getData(json, BrandData.class);
        for (ModelDataInfo m : brandData.modelDataInfo()) {
            System.out.println("Modelo: "+m.modelName()+" --- Codigo: "+m.code());
        }

        System.out.println("Digite um trecho do nome do modelo que deseja pesquisar: ");
        sc.nextLine();
        String searchModel = sc.nextLine();
        Stream <ModelDataInfo> research = brandData.modelDataInfo().stream()
                .filter(m -> m.modelName().toUpperCase().contains(searchModel.toUpperCase()));

        research.forEach(System.out::println);

        System.out.println("Qual o codigo do modelo que deseja visualizar? ");
        int modelCode = sc.nextInt();
        json = requestAPI.getData(ADDRESS + vehicleType + "/marcas/" + brandCode + "/modelos/"+modelCode+"/anos");
        List<YearData> yearDataList = conversion.getDataList(json, YearData.class);
        List<String> yearCodes = new ArrayList<>();
        for (YearData yd:yearDataList) {
            yearCodes.add(yd.code());
        }
        for (String yearCode:yearCodes) {
            json = requestAPI.getData(ADDRESS + vehicleType + "/marcas/" + brandCode + "/modelos/"+modelCode+"/anos/"+yearCode);
            ModelData modelData = conversion.getData(json, ModelData.class);
            System.out.println(modelData.model()+"  Ano: "+modelData.year()+"  Valor: "+modelData.value()+"  Combustivel: "+modelData.fuelType());
        }

    }
}