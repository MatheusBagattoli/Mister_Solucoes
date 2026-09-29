package com.senai.almoxarifado_Mister.sessao;

import com.senai.almoxarifado_Mister.entities.EstoqueEntity;

import java.util.List;

public class AlgoritmoOrdenacao {

    public static void ordenar(List<EstoqueEntity> lista, String criterio) {

        for (int i = 1; i < lista.size(); i++) {
            EstoqueEntity atual = lista.get(i);
            int j = i - 1;

            while (j >= 0 && deveVirDepois(lista.get(j), atual, criterio)) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, atual);
        }
    }

    private static boolean deveVirDepois(EstoqueEntity primeiro, EstoqueEntity segundo, String criterio) {
        if ("quantidade".equalsIgnoreCase(criterio)) {
            return primeiro.getQuantidade() > segundo.getQuantidade();
        } else if ("estoqueMinimo".equalsIgnoreCase(criterio)) {
            return primeiro.getProduto().getEstoqueMinimo() > segundo.getProduto().getEstoqueMinimo();
        } else {
            return primeiro.getProduto().getNome().compareToIgnoreCase(segundo.getProduto().getNome()) > 0;
        }
    }
}

