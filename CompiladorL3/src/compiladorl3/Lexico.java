package compiladorl3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author Equipe: Do Prata ao Global
 */
public class Lexico {

	private char[] conteudo;
	private int indiceConteudo;

	public Lexico(String caminhoCodigoFonte) {
		try {
			String conteudoStr;
			conteudoStr = new String(Files.readAllBytes(Paths.get(caminhoCodigoFonte)));
			this.conteudo = conteudoStr.toCharArray();
			this.indiceConteudo = 0;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// lookahead, retorna proximo chat após o token
	public char lookahead() {
		int i = this.indiceConteudo;
		return this.conteudo[i+1];
	}

	// Retorna próximo char
	private char nextChar() {
		return this.conteudo[this.indiceConteudo++];
	}

	// Verifica existe próximo char ou chegou ao final do código fonte
	private boolean hasNextChar() {
		return indiceConteudo < this.conteudo.length;
	}

	// Retrocede o índice que aponta para o "char da vez" em uma unidade
	private void back() {
		this.indiceConteudo--;
	}

	// Identificar se char é letra minúscula
	private boolean isLetra(char c) {
		return (c >= 'a') && (c <= 'z');
	}

	// Identificar se char é dígito
	private boolean isDigito(char c) {
		return (c >= '0') && (c <= '9');
	}

	// Identificar se é um caractere válido para char
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
	}

	// Identificar se é um operador relacional
	private boolean isOperatorRel(char c) {
		return (c == '>' || c == '<');
	}

	// Identificar se é um operador aritmetico
	private boolean isOperatorArit(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%');
	}

	// Identificação de um espaço
	private boolean isCharacterSpace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}

	// Identificação de caracteres especiais
	private boolean isCharacterSpecial(char c) {
		return (c == ')' || c == '(' || c == '{' || c == '}' || c == ',' || c == ';');
	}

	// Identificar operador de atribuição
	private boolean isOperatorAtrib(char c) {
		return (c == '=');
	}

	private boolean isPalavraReservada(String p) {
		return p.equals("int") || p.equals("float") || p.equals("char") ||
				p.equals("while") || p.equals("main") || p.equals("if") || p.equals("else");
	}

	// Método retorna próximo token válido ou retorna mensagem de erro.
	public Token nextToken() {
		Token token = null;
		char c;
		int estado = 0;

		StringBuffer lexema = new StringBuffer();
		while (this.hasNextChar()) {
			c = this.nextChar();
			switch (estado) {
				case 0:
					if (this.isCharacterSpace(c)) {
						estado = 0;
					} else if (this.isLetra(c) || c == '_') {
						lexema.append(c);
						estado = 1;
					} else if (this.isDigito(c)) {
						lexema.append(c);
						estado = 2;
					} else if (this.isCharacterSpecial(c)) {
						lexema.append(c);
						estado = 5;
					} else if (c == '\'') {
						lexema.append(c);
						estado = 7;
					} else if (this.isOperatorAtrib(c)) {
						lexema.append(c);
						estado = 6;
					} else if (isOperatorRel(c)) {
						lexema.append(c);
						estado = 10;
					} else if (this.isOperatorArit(c)) {
						lexema.append(c);
						estado = 11;
					} else if (c == '$') {
						lexema.append(c);
						estado = 99;
						this.back();
					} else {
						lexema.append(c);
						throw new RuntimeException("Erro: token inválido \"" + lexema.toString() + "\"");
					}
					break;
				case 1:
					if (this.isLetra(c) || this.isDigito(c) || c == '_') {
						lexema.append(c);
						estado = 1;
						if (this.isPalavraReservada(lexema.toString())) {
							estado = 12;
						}
					} else {
						this.back();
						return new Token(lexema.toString(), Token.TIPO_IDENTIFICADOR);
					}
					break;
				case 2:
					if (this.isDigito(c)) {
						lexema.append(c);
						estado = 2;
					} else if (c == '.') {
						lexema.append(c);
						estado = 3;
					} else {
						this.back();
						return new Token(lexema.toString(), Token.TIPO_INTEIRO);
					}
					break;
				case 3:
					if (this.isDigito(c)) {
						lexema.append(c);
						estado = 4;
					} else {
						this.back();
						throw new RuntimeException(
								"Erro: número float está com formato inválido \"" + lexema.toString() + "\"");
					}
					break;
				case 4:
					if (this.isDigito(c)) {
						lexema.append(c);
						estado = 4;
					} else {
						this.back();
						return new Token(lexema.toString(), Token.TIPO_REAL);
					}
					break;
				case 5:
					this.back();
					return new Token(lexema.toString(), Token.TIPO_CARACTER_ESPECIAL);
				case 6:
					if (c != '=') {
						back();
						return new Token(lexema.toString(), Token.TIPO_OPERADOR_ATRIBUICAO);
					} else if (c == '=') {
						lexema.append(c);
						return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);
					}
					break;
				case 7:
					lexema.append(c);
					if (this.isChar(c)) {
						estado = 8;
					} else {
						throw new RuntimeException(
								"Erro: formato de char inválido \"" + lexema.toString() + "\"");
					}
					break;
				case 8:
					if (c == '\'') {
						lexema.append(c);
						estado = 9;
						return new Token(lexema.toString(), Token.TIPO_CHAR);
					} else {
						this.back();
						throw new RuntimeException(
								"Erro: char está com formado incorreto \"" + lexema.toString() + "\"");
					}
				case 10:
					if (c == '=') {
						lexema.append(c);
						return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);
					} else if (c != '=') {
						back();
						return new Token(lexema.toString(), Token.TIPO_OPERADOR_RELACIONAL);
					}
					break;
				case 11:
					back();
					return new Token(lexema.toString(), Token.TIPO_OPERADOR_ARITMETICO);
				case 12:
					if (c == ' ' || c == '\t' || c == '\n' || c == '\r' || this.isCharacterSpecial(c) ) {
						this.back();
						return new Token(lexema.toString(), Token.TIPO_PALAVRA_RESERVADA);
					} else {
						lexema.append(c);
						estado = 1;
						break;
					}
				case 99:
					return new Token(lexema.toString(), Token.TIPO_FIM_CODIGO);
			}
		}
		return token;
	}
}
