package repository;

import models.informacoes;
import config.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class informacoesRepository {

    // Cria um novo registro de informações
    public void adicionarInformacoes(informacoes informacoes) {
        String sql = "INSERT INTO informacoes (nome_album, nome_artista, nome_gravadora, divulgacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, informacoes.getNomeAlbum());
            stmt.setString(2, informacoes.getNomeArtista());
            stmt.setString(3, informacoes.getNomeGravadora());
            stmt.setString(4, informacoes.getDivulgacao());
            //tem q adicionar o getId aq??

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Informações adicionadas com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar as informações");
            e.printStackTrace();
        }
    }

    // Obter todas as informações
    public List<informacoes> obterTodasInformacoes() {
        List<informacoes> listaInformacoes = new ArrayList<>();
        String sql = "SELECT * FROM informacoes";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                informacoes informacao = new informacoes( //mudei para o nome novo das tabelas
                    rs.getInt("id"),
                    rs.getString("nome_album"),
                    rs.getString("nome_artista"),
                    rs.getString("nome_gravadora"),
                    rs.getString("divulgacao")
                );
                listaInformacoes.add(informacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter informações");
            e.printStackTrace();
        }

        return listaInformacoes; // Corrigido para retornar a lista corretamente
    }

    // Obter informações por ID
    public informacoes obterInformacaoPorId(int id) {
        String sql = "SELECT * FROM informacoes WHERE id = ?";
        informacoes informacao = null;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                informacao = new informacoes( //mudei para o nome novo das tabelas
                    rs.getInt("id"), 
                    rs.getString("nome_album"),
                    rs.getString("nome_artista"),
                    rs.getString("nome_gravadora"),
                    rs.getString("divulgacao")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao obter informações por ID");
            e.printStackTrace();
        }

        return informacao; // Retorna a informação correspondente
    }

    // Atualizar uma informação
    public void atualizarInformacao(informacoes informacao) {
        String sql = "UPDATE informacoes SET nome_album = ?, nome_artista = ?, nome_gravadora = ?, divulgacao = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            
                    stmt.setString(1, informacao.getNomeAlbum());
                    stmt.setString(2, informacao.getNomeArtista());
                    stmt.setString(3, informacao.getNomeGravadora());
                    stmt.setString(4, informacao.getDivulgacao());
                    stmt.setInt(5, informacao.getId());
            
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Contato atualizado com sucesso!");
                } else {
                    System.out.println("Contato não encontrado");
                }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar contato");
            e.printStackTrace();
        }
    }

    // Deletar uma informação

    public void deletarInformacao(int id) {
        String sql = "DELETE FROM informacoes WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Informação deletado com sucesso!");
            }else {
                System.out.println("Informação não encontrado");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar informação");
            e.printStackTrace();
        }
    }
}