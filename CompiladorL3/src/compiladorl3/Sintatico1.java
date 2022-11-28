package compiladorl3;

import java.io.FileNotFoundException;

import javax.management.RuntimeErrorException;

public class Sintatico1 {

    private Lexico lexico;
    private Token token;
    private ListaCircular s = new ListaCircular();
    private ListaCircularNode auxToken;

    public Sintatico1(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S() {
        this.token = this.lexico.proxToken();
        this.P();
        if (this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }
    }

    // programa
    private void P() {
        if (!this.token.getLexema().equals("int")) {
            System.out.println(token.getLexema());
            throw new RuntimeException("oops, erro de sintaxe no cabeçalho do programa");
        } else {
            this.token = this.lexico.proxToken();
        }

        if (!this.token.getLexema().equals("main")) {

            throw new RuntimeException("oops, erro de sintaxe no cabeçalho do programa");
        } else {
            this.token = this.lexico.proxToken();
        }

        if (!this.token.getLexema().equals("(")) {
            throw new RuntimeException("oops, faltou o '()' no cabeçalho após 'main'");
        } else {
            this.token = this.lexico.proxToken();
        }

        if (!this.token.getLexema().equals(")")) {
            throw new RuntimeException("oops, esqueceu de fechar o ) antes de " + token.getLexema());
        } else {
            this.token = this.lexico.proxToken();
            B();
        }
    }

    // bloco
    private void B() {
        if (!this.token.getLexema().equals("{")) {
            throw new RuntimeException("Oxe, esqueceu de iniciar o bloco com '{'? antes de " + this.token.getLexema());
        } else {
            this.token = this.lexico.proxToken();
            this.B1();
        }

    }

    private void B1() {
        if (this.token.getLexema().equals("}")) {
            this.token = lexico.proxToken();
            return;
        } else {
            this.V();
            this.K();
            this.B1();
        }
    }

    // declaração de variavel
    private void V() {
        if (!(this.token.getTipo() == Token.TIPO_PALAVRA_RESERVADA)) {
            return;
        } else {
            if (!(this.token.getLexema().equals("int") ||
                    this.token.getLexema().equals("char") ||
                    this.token.getLexema().equals("float"))) {
                return;
            } else {
                Token temp = this.token;
                this.token = lexico.proxToken();
                if (!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR)) {
                    throw new RuntimeException(
                            "Oops, era pra ter um indentificador depois de '" + temp.getLexema() + "'");
                } else {
                    int tipo = this.token.getTipo();
                    String variable = this.token.getLexema();
                    ListaCircularNode d = s.search(variable);

                    if (d!=null) {
                        throw new RuntimeException("Oxe, a variável já foideclarada: " + this.token.getLexema());
                    }
                    s.addLast(tipo, variable);
                    this.token = this.lexico.proxToken();
                    if (!this.token.getLexema().equals(";")) {
                        throw new RuntimeException(
                                "Oops, faltou o ';' para finalizar a linha depois de '" + temp.getLexema() + "'");
                    } else {
                        this.token = lexico.proxToken();
                    }
                }
            }
        }
    }

    // comando
    private void K() {
        if ((this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getLexema().equals("{"))) {
            KB();
        } else if (this.token.getLexema().equals("while")) {
            IT();
        } else if (this.token.getLexema().equals("if")) {
            CD();
        }
    }

    // condicional
    private void CD() {
        this.token = this.lexico.proxToken();
        if (!this.token.getLexema().equals("(")) {
            throw new RuntimeException("Oops, faltou abrir o '('' após o if");
        } else {

            CD1();
        }
    }

    private void CD1() {
        this.token = this.lexico.proxToken();
        ER();
        if (!this.token.getLexema().equals(")")) {
            throw new RuntimeException("Oops, faltou fechar o ')' após a expressão");
        } else {
            this.token = this.lexico.proxToken();
            K();
        }
    }

    // comando basico
    private void KB() {
        AT();
        if (this.token.getLexema().equals("{")) {
            this.token = this.lexico.proxToken();
            B1();
        }
    }

    // Atribuição
    private void AT() {
        if ((this.token.getTipo() == Token.TIPO_IDENTIFICADOR)) {
            String var = this.token.getLexema();
            auxToken = s.search(var);
            if(this.token == null) {
                throw new RuntimeException("Variável não foi declarada"+this.token.getLexema());
            }
        }
        this.token = this.lexico.proxToken();
        if (!this.token.getLexema().equals("=")) {
            throw new RuntimeException("Oops, faltou o sinal de '='" +this.token.getLexema());
        }
        this.token = this.lexico.proxToken();
        AT1();
        if (!this.token.getLexema().equalsIgnoreCase(";")) {
             throw new RuntimeException("Oops, faltou o ';'" + this.token.getLexema());
        }
        this.token = this.lexico.proxToken();
    }

    private void AT1() {
        this.E();
        if (!(this.token.getLexema().equals(";"))) {
            throw new RuntimeException(
                    "Oops, era pra ter finalizado a linha com ';' antes de inserir " + token.getLexema());
        } else {
            this.token = this.lexico.proxToken();
        }
    }

    // iteração
    private void IT() {
        this.token = this.lexico.proxToken();
        if (this.token.getLexema().equals("(")) {
            IT1();
        } else {
            throw new RuntimeException("oops, era pra ter uma expressão relacional depois de 'while");
        }
    }

    private void IT1() {
        this.token = this.lexico.proxToken();
        try {
            ER();
        } catch (Exception E) {
            System.out.println("Faltou uma expressão aritimetica antes de fechar o no while");
            System.exit(0);
        }

        if (!this.token.getLexema().equals(")")) {
            throw new RuntimeException("Precisa fechar o ')' após a expressão");
        } else {
            this.token = lexico.proxToken();
            K();
        }

    }

    // expressão relacional
    private void ER() {

        E();

        if (!(this.token.getTipo() == Token.TIPO_OPERADOR_RELACIONAL)) {
            throw new RuntimeException("precisa inserir uma expressão relacional após o '");
        } else {
            this.token = this.lexico.proxToken();
            E();
        }
    }

    // expressão aritimetica
    private void E() {
        this.T();
        this.E1();
    }

    private void E1() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.E1();
        } else {

        }
    }

    private void T() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO
                || this.token.getTipo() == Token.TIPO_REAL) {
            this.token = this.lexico.proxToken();
        } else {
            throw new RuntimeException(
                    "Oxe, era para ser um identificador" + "ou número pertinho de" + this.token.getLexema());
        }
    }

    private void OP() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.token = this.lexico.proxToken();
        } else {
            throw new RuntimeException(
                    "Oxe, era para ser um operador" + "artimético (+,-,/,*) pertinho de" + this.token.getLexema());
        }
    }
}