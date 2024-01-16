package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.*;
import br.com.alura.TabelaFipe.service.RequestAPI;
import java.util.List;
import java.util.Scanner;

public class Main {

    DataConversion conversion = new DataConversion();
    private RequestAPI requestAPI = new RequestAPI();
    private final String ADDRESS = "https://parallelum.com.br/fipe/api/v1/";

    public void showMenu() {
        System.out.println("Bem-vindo ao sistema de pesquisa da tabela FIPE");
        System.out.println("Deseja pesquisar por 'carros', 'motos' ou 'caminhoes?'");
        Scanner sc = new Scanner(System.in);
        String search = sc.nextLine();

        var json = requestAPI.getData(ADDRESS + search + "/marcas");
        List<VehicleTypeData> vehicleTypeDataList = conversion.getDataList(json, VehicleTypeData.class);
        vehicleTypeDataList.forEach(v -> System.out.println("Marca: " + v.brand() + " ---  Codigo: " + v.code()));

        System.out.println("Digite o codigo da marca que deseja pesquisar: ");
        int brandCode = sc.nextInt();
        json = requestAPI.getData(ADDRESS + search + "/marcas/" + brandCode + "/modelos");
        BrandData brandData = conversion.getData(json, BrandData.class);
        for (ModelDataInfo m : brandData.modelDataInfo()) {
            System.out.println("Modelo: "+m.modelName()+" --- Codigo: "+m.code());
        }
        System.out.println("Escolha o modelo que deseja pesquisar por meio do codigo pertencente a ele: ");
        int modelCode = sc.nextInt();
        json = requestAPI.getData(ADDRESS + search + "/marcas/" + brandCode + "/modelos/"+modelCode+"/anos");
        List<YearData> yearDataList = conversion.getDataList(json, YearData.class);
        for (YearData yd:yearDataList
             ) {
            System.out.println("Ano/Tipo Combustivel: "+yd.yearAndFuel()+" ------- Codigo: "+yd.code());
        }
        System.out.println("Escolha o modelo desejado por meio de seu codigo: ");
        sc.nextLine();
        String yearCode = sc.nextLine();
        json = requestAPI.getData(ADDRESS + search + "/marcas/" + brandCode + "/modelos/"+modelCode+"/anos/"+yearCode);
        ModelData modelData = conversion.getData(json, ModelData.class);
        System.out.println("Nome do modelo: "+modelData.model()+"\nMarca: "+modelData.brandName()+"\nAno: "+modelData.year()+"\n" +
                "Valor: "+modelData.value());

        }
}