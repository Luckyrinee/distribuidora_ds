package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import models.informacoes;

import java.awt.*;
import java.util.List;

public class Pag2 extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public Pag2() {
        super("Distribuidora - Listar");
        initializeComponents();
    }

    private void initializeComponents() {
        String[] columnNames = {"ID", "Artista", "Álbum", "Gravadora", "Divulgação"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void atualizarTabela(List<informacoes> informacoes) {
        tableModel.setRowCount(0); //Limpa a tabela
        for (informacoes informacao : informacoes) {
            Object[] row = {
                informacao.getId(),
                informacao.getNomeAlbum(),
                informacao.getNomeArtista(),
                informacao.getNomeGravadora(),
                informacao.getDivulgacao()
            };
            tableModel.addRow(row);
        }
    }

    public int getSelectedInformacaoId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }
}
