package report;

import model.Pedido;

import java.util.List;

public interface RelatorioVendas {
    void gerar(List<Pedido> pedidos);
}