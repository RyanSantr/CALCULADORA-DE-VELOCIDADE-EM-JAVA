package aplication;

import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CalculadoraVelocidade extends JFrame {
	
	// criando os array de distancia e tempo 
	
	private ArrayList<Double> distancias = new ArrayList<>();
	private ArrayList<Double> tempos = new ArrayList<>();
	private int contador = 0;
	private JToolBar toolbar;
	
	
	private void configurarCores() {
		
		Color corFundo = new Color(240, 248, 255);
		Color corPainel = new Color(255, 255, 255);
		Color corBotao = new Color(70, 130, 180);
		Color corTextoBotao = Color.WHITE;
		
		getContentPane().setBackground(corFundo);
		
		for(Component comp : toolbar.getComponents()) {
			if(comp instanceof JButton) {
				JButton botao = (JButton) comp;
				botao.setBackground(corBotao);
				botao.setForeground(corTextoBotao);
				botao.setFont(new Font("Arial", Font.BOLD, 14));
				botao.setFocusPainted(false); // removendo borda de foco
				
				
			}
		}
	}
	
	// metodo para adicionar partes do percurso
	public void AdicionandoPartes(double distancia, double tempo) {
		distancias.add(distancia);
		tempos.add(tempo);
		contador++;
		
		char LetraInicio = (char) ( 'A' + (contador - 1));
		char letraFim = (char)('A' + contador);
		
		System.out.println("Trecho " + LetraInicio + "-> " + letraFim + ": " + distancia + " km em " + tempo + " min");	
		System.out.println();
		
		
	}
	
	// calculadora em km com o tempo e distancia percorrida
	
	public String Calcularmedia() {
		
		if(distancias.isEmpty()) {
			return "nenhum Techo adicionado";
		}
		
		
		double somaDistancia = 0;
		double somaTempo = 0;
	
		for( int i = 0; i < distancias.size(); i++) {
			somaDistancia += distancias.get(i);
			somaTempo += tempos.get(i);
		}
	
		double velocidadeMedia = somaDistancia / ( somaTempo / 60);
		
		String resultado = String.format(
				" VELOCIDADE MÉDIA: %.2f km/h \n" + "DISTANCIA TOTAL: %.2f km\n" + "TEMPO TOTAL: %.2f min ( %.2f h)\n" , velocidadeMedia, somaDistancia, somaTempo, somaTempo / 60);
				
				
		System.out.println(resultado);
		return resultado;
	
	}
	
    public CalculadoraVelocidade() {
        
        setTitle("CALCULADORA DE VELOCIDADE");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout());
        
    
    // Adicionando a toolbar
    
    toolbar = new JToolBar(); 
    toolbar.setFloatable(false);
    toolbar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
    // criando os botoes
    
    JButton btnAdicionar = new JButton("ADICIONAR");
    JButton btnCalcular = new JButton("CALCULAR");
    JButton btnLimpar = new JButton("LIMPAR");
    
    //Adicionando os botoes
   
    toolbar.add(btnAdicionar);
    toolbar.add(btnCalcular);
    toolbar.addSeparator();
    toolbar.add(btnLimpar);
    
    // pre definicao da toolbar
    toolbar.setPreferredSize(new Dimension(0, 60));
    
    // adicionando no frame java
    add(toolbar, BorderLayout.SOUTH);    
    // margens gerais
    toolbar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    //entrada dos dados
    
    JPanel painelEntrada = new JPanel(new GridLayout(2, 2, 10, 10));
    painelEntrada.setBorder(BorderFactory.createTitledBorder("CALCULADORA DE VELOCIDADE MEDIA"));
    
    painelEntrada.add(new JLabel("Distancia (km)"));
    JTextField txtDistancia = new JTextField();
    painelEntrada.add(txtDistancia);
    
    painelEntrada.add(new JLabel("Tempo (min) "));
    JTextField txtTempo = new JTextField();
    painelEntrada.add(txtTempo);
    
    add(painelEntrada, BorderLayout.NORTH);
    
    
    
    // area de texto
    JTextArea areaTexto = new JTextArea();
    areaTexto.setEditable(false);
    add(new JScrollPane(areaTexto), BorderLayout.CENTER);
    
    // adicionando o botao e funcão de adicionar trajetos/ trechos
    btnAdicionar.addActionListener(e -> {
    	try {
    		

    		double distancia = Double.parseDouble(txtDistancia.getText());
    		double tempo = Double.parseDouble(txtTempo.getText());
    		
    		// verificação de distancia e tempo
    		if (distancia <= 0 || tempo <= 0 ) {
    			areaTexto.append("ERRO: Distancia e Tempo inválidos \n");
    			
    			JOptionPane.showMessageDialog(null, "DISTANCIA E TEMPOS DEEM SER MAIORES QUE 0!! ", 
    					"VALORES INVÁLIDOS", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		// chegou aqui pós verificação
    		AdicionandoPartes(distancia, tempo);
    		
    		// mostrando area
    		areaTexto.append("Percurso Adicionado " + distancia + "km em " + tempo + "min \n");
    		
    		txtDistancia.setText("");
    		txtTempo.setText("");
    		
    		
    		
    	}catch (NumberFormatException ex) {
    		areaTexto.append("Erro Digite número Valido\n");
    		JOptionPane.showMessageDialog(null, "Digite apennas números ! EX 10.5 ", "ERRO DE FORMATO", JOptionPane.ERROR_MESSAGE);
    	}
    });
    
    // botao para chamar o metodo de calcular a velocidade media
    
    btnCalcular.addActionListener(e -> {
    		String resultado = Calcularmedia();
    		areaTexto.append(resultado);
    });
    
    // botao para limpar os dados e iniciar novamente um trajeto novo
    
    btnLimpar.addActionListener(e -> {
    	
    	int resposta = JOptionPane.showConfirmDialog(this, 
    			"TEM CERTEZA QUE DESEJA LIMPAR TODO O TRAJETO? ", 
    			"CONFIRMAR LIMPEZA",
    			JOptionPane.YES_NO_OPTION, 
    			JOptionPane.QUESTION_MESSAGE);
    	
    	if ( resposta == JOptionPane.YES_OPTION) {
    		distancias.clear();
    		tempos.clear();
    		contador = 0;
    		
    		areaTexto.setText("");
    		txtDistancia.setText("");
    		txtTempo.setText("");
    		
    		areaTexto.append("DADOS ANTIGOS LIMPOS\n");
    		areaTexto.append("DIGITE NOVOS DADOS DE TRAJETOS\n");
    		
    		txtDistancia.requestFocus();
    		
    	}
    });
    configurarCores();
    }  
    
    // O MAIN PARA EXECUTAR
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraVelocidade().setVisible(true);
        });
    }
}
