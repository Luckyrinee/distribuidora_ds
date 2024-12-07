package views;

import javax.swing.*;

import models.informacoes;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Pag1 extends JDialog {
    private JLabel labelAlbum, labelArtista, labelAddMusica, labelGravadora, labelCapa, labelDivulgar, labelGenero;
     //Talvez deletar
    private JTextField nomeAlbum, nomeArtista, nomeGravadora;
    private JButton salvarButton, cancelarButton, btnCarregaImagem, btnAdicionarMusica;  // Botão pra adicionar música
    private JCheckBox checkSpotify, checkYT, checkApple;
    @SuppressWarnings("rawtypes") //evita avisos do compilador relacionado a combobox
    private JComboBox generoJComboBox;

    private informacoes informacao;
    private boolean isEditMode; //diz se o formulário está sendo editado ou nao

    private JFileChooser  chooser; //é um seletor de imagens
    private BufferedImage imagem; //armazena a imagem adicionada
    private File arquivo; 

    private JPanel panel1, panel2, panel3, panel4, panelCol2, checkJPanel;

    //construtor q indica q a pag1 está em construção
    public Pag1(Frame parent, String title) {
        super(parent, title, true);
        this.isEditMode = false;
        initializeComponents();
    }

    //construtor usado pra quaando abrir a pagina no modo edição
    public Pag1(Frame parent, String title, informacoes informacao) {
        super(parent, title, true);
        this.informacao = informacao;
        this.isEditMode = true;
        initializeComponents();
        preencherCampos();
    }

    private void initializeComponents() {


        // Panel1 (label + text field)

        labelAlbum = new JLabel ("Nome do álbum");
        nomeAlbum = new JTextField (25); //mudei o nome da variavel

        labelArtista = new JLabel ("Nome do artista");
        nomeArtista = new JTextField (25); //mudei o nome da variavel

        labelAddMusica = new JLabel ("Adicionar músicas");

        labelGravadora = new JLabel ("Nome da gravadora");
        nomeGravadora = new JTextField (25); //mudei o nome da variavel
        

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(8,1));

        panel1.add(labelAlbum); //mudei o nome da variavel
        panel1.add(nomeAlbum);
        panel1.add(labelArtista);
        panel1.add(nomeArtista); //mudei o nome da variavel

        //adiciona um action listener para o botão adicionar musica
        btnAdicionarMusica = new JButton("Adicionar Música");
        btnAdicionarMusica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirEscolherMusicas();
            }
        });

        panel1.add(labelAddMusica);
        panel1.add(btnAdicionarMusica); // Adicionando o botão no painel

        panel1.add(labelGravadora);
        panel1.add(nomeGravadora);


        // Panel2 (labelCapa + imagem)

        labelCapa = new JLabel ("Capa do álbum");
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout()); //usando borderlayout pra a imagem ficar centralizada.

        panel2.add(labelCapa, BorderLayout.NORTH);

        //quando o botão carregar imagem é clicado, é carregado uma imagem
        btnCarregaImagem = new JButton("Carregar Imagem");
        btnCarregaImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarImagem();
            }
        });

        panel2.add(btnCarregaImagem, BorderLayout.SOUTH);  // Adicionando o botão no painel

        // Panel3 (CheckBox + ComboBox)
        labelDivulgar = new JLabel ("Divulgar em");
        checkSpotify = new JCheckBox ("Spotify");
        checkYT = new JCheckBox ("Yt Music");
        checkApple = new JCheckBox ("Apple Music");

        //permite q apenas uma checkbox seja selecionada
        ItemListener a = e ->{
            JCheckBox src = (JCheckBox) e.getItem();
            if (src.isSelected()) {
                checkSpotify.setSelected(src == checkSpotify);
                checkYT.setSelected(src == checkYT);
                checkApple.setSelected(src == checkApple);
            }
        };

        checkSpotify.addItemListener(a);
        checkYT.addItemListener(a);
        checkApple.addItemListener(a);

        checkJPanel = new JPanel();
        checkJPanel.setLayout(new GridLayout(1,3));
        checkJPanel.add (checkSpotify);
        checkJPanel.add (checkYT);
        checkJPanel.add (checkApple);

        // ComboBox para gênero
        labelGenero = new JLabel("Gênero do álbum");
        generoJComboBox = new JComboBox<>();
        generoJComboBox.addItem("Pop");
        generoJComboBox.addItem("Rap");
        generoJComboBox.addItem("Eletrônico");
        generoJComboBox.addItem("Rock");
        generoJComboBox.addItem("Outro");

        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4,1));
        panel3.add (labelDivulgar);
        panel3.add (checkJPanel);
        panel3.add (labelGenero);
        panel3.add (generoJComboBox);

        panelCol2 = new JPanel();
        panelCol2.setLayout(new GridLayout(2,1));
        panelCol2.add (panel2);
        panelCol2.add (panel3);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");
        panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.add (salvarButton);
        panel4.add (cancelarButton);

        Container janela;
        janela = getContentPane();
        janela.setLayout(new BorderLayout());
        janela.add (panel1, BorderLayout.WEST);
        janela.add (panelCol2, BorderLayout.EAST);
        janela.add (panel4, BorderLayout.SOUTH);

        //adiciona funcionalidades pro botão de salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    if (isEditMode) {
                        atualizarInformacao();
                    } else {
                        adicionarInformacao();
                    }
                    dispose(); // Fecha a janela após salvar
                }
            }
        });

        //adiciona funcionalidades pro botão de salvar
        cancelarButton.addActionListener(e -> dispose());
    }
    

    // método pra abrir o seletor de músicas
    private void abrirEscolherMusicas() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Escolha as músicas");
        chooser.setMultiSelectionEnabled(true);  // permite selecionar vários arquivos

        int resultado = chooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File[] arquivosSelecionados = chooser.getSelectedFiles();
            for (File file : arquivosSelecionados) {
                System.out.println("Música selecionada: " + file.getAbsolutePath());
            }
        }
    }

    // método pra carregar a imagem
    private void carregarImagem() {
        chooser = new JFileChooser();
        chooser.setDialogTitle("Escolha uma imagem");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg"));
        
        int resultado = chooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            arquivo = chooser.getSelectedFile();
            try {
                imagem = ImageIO.read(arquivo);
                atualizarImagem();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // método pra atualizar a imagem
    private void atualizarImagem() {
    JPanel painelImagem = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagem != null) {
                // redimensiona a imagem pra q ela se ajuste ao tamanho do painel
                Image imagemRedimensionada = imagem.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                g.drawImage(imagemRedimensionada, 0, 0, this);
            }
        }
    };

    painelImagem.setPreferredSize(new Dimension(200, 150));  // definindo o tamanho da imagem
    panel2.add(painelImagem, BorderLayout.CENTER);
    panel2.revalidate(); 
    panel2.repaint();    
    



        

        this.add(panel1);
        this.pack();
        this.setLocationRelativeTo(getParent());
    }

    //permite q os campos durante o modo de edição sejam preenchidos com as informações q devem ser editadas
    private void preencherCampos() {
        if (informacao != null) {

            nomeAlbum.setText(informacao.getNomeAlbum()); //mudei o nome da variavel
            nomeArtista.setText(informacao.getNomeArtista());
            nomeGravadora.setText(informacao.getNomeGravadora());
            checkSpotify.isSelected();
            checkYT.isSelected();
            checkApple.isSelected();
        }
    }
    //verifica se tem algum erro nas informações adicionadas, como por ex um campo vazio
    private boolean validarCampos() {
        if (nomeAlbum.getText().trim().isEmpty() || nomeArtista.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do album e do artista são obrigatórios", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void adicionarInformacao() {
        String divulgacao = ""; 
        if (checkSpotify.isSelected()) {
            divulgacao += "Youtube Music";
        }
        if (checkYT.isSelected()) {
            divulgacao += "Spotify";
        }
        if (checkApple.isSelected()){
            divulgacao += "Apple Music";
        }  
        informacao = new informacoes(
        nomeAlbum.getText().trim(),
        nomeArtista.getText().trim(),
        nomeGravadora.getText().trim(),
        divulgacao

    );
}

    private void atualizarInformacao() {
        if (informacao != null) {
            String divulgacao = ""; 
        if (checkSpotify.isSelected()) {
            divulgacao += "Youtube Music";
        }
        if (checkYT.isSelected()) {
            divulgacao += "Spotify";
        }
        if (checkApple.isSelected()){
            divulgacao += "Apple Music";
        } 
            informacao.setNomeAlbum(nomeAlbum.getText().trim());
            informacao.setNomeArtista(nomeArtista.getText().trim());
            informacao.setNomeGravadora(nomeGravadora.getText().trim());
            informacao.setDivulgacao(divulgacao.trim());
        }
    }

    public informacoes getInformacao() {
        return informacao;
    }



}

