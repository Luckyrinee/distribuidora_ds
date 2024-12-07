package controller;

import models.informacoes;
import repository.informacoesRepository;
import views.Pag1;
import views.Pag2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class informacoesController {
    private informacoesRepository repository;
    private Pag2 tableView;

    public informacoesController() {
        repository = new informacoesRepository();
        tableView = new Pag2();
        inicializar();
    }

    private void inicializar(){
        // Atualizar a tabela com os contatos existentes
        atualizarTabela();

        //Cria a barra de ferramentas com botões
        JToolBar toolBar = new JToolBar();
        JButton adicionarButton = new JButton("Adicionar");
        JButton editarButton = new JButton("Editar");
        JButton deletarButton = new JButton("Deletar");
        toolBar.add(adicionarButton);
        toolBar.add(editarButton);
        toolBar.add(deletarButton);

        tableView.add(toolBar, java.awt.BorderLayout.NORTH);
        
        //Ações dos botões
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarInformacao();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarInformacao();
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarInformacao();
            }
        });

        tableView.setVisible(true);
    }

    private void atualizarTabela () {
        List<informacoes> Pag2 = repository.obterTodasInformacoes();
        tableView.atualizarTabela(Pag2);
    }

    private void adicionarInformacao() {
        Pag1 form = new Pag1(tableView, "Adicionar informação");
        form.setSize(650,400);
        form.setVisible(true);
        informacoes novaInformacao = form.getInformacao();
        if (novaInformacao != null) {
            repository.adicionarInformacoes(novaInformacao);
            atualizarTabela();
        }
    }

    private void editarInformacao() {
        int selectedId = tableView.getSelectedInformacaoId();
        if (selectedId != -1) {
            informacoes informacao = repository.obterInformacaoPorId(selectedId);
            if (informacao != null) {
                Pag1 form = new Pag1(tableView, "Editar informacao", informacao);
                form.setSize(650,400);
                form.setVisible(true);
                informacoes informacaoAtualizada = form.getInformacao();
                if (informacaoAtualizada != null) {
                    informacaoAtualizada = new informacoes(
                        selectedId,
                        informacaoAtualizada.getNomeAlbum(),
                        informacaoAtualizada.getNomeArtista(),
                        informacaoAtualizada.getNomeGravadora(),
                        informacaoAtualizada.getDivulgacao()
                    );
                    repository.atualizarInformacao(informacaoAtualizada);
                    atualizarTabela();
                }
            }else {
                JOptionPane.showMessageDialog(tableView, "Informacao não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(tableView, "Selecione uma informacao para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletarInformacao() {
        int selectId = tableView.getSelectedInformacaoId();
        if (selectId != -1) {
            int confirm = JOptionPane.showConfirmDialog(tableView, "Tem certeza que deseja deletar esta informacao?", "Confirmar deleção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_NO_OPTION) {
                repository.deletarInformacao(selectId);
                atualizarTabela();
            }
        }else {
            JOptionPane.showMessageDialog(tableView, "Selecione uma informação para deletar", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void iniciar() {
        // Ações já são inicializadas no construtor
    }
}

