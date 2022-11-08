package compiladorl3;

public class Sintatico {

    private Lexico lexico;
    private Token token;

    public Sintatico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void S() {
        this.token = this.lexico.nextToken();
        this.E();
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        }
    }

    private void E() {
        this.T();
        this.E1();
    }

    private void E1() {
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.E1();
        }
        else {

        }
    }

    private void T() {
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO || this.token.getTipo() == Token.TIPO_REAL) {
            this.token = this.lexico.nextToken();
        }
        else {
            throw new RuntimeException("Oxe, era para ser um identificador" + "ou número pertinho de" + this.token.getLexema());
        }
    }

    private void OP() {
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.token = this.lexico.nextToken();
        }
        else {
            throw new RuntimeException("Oxe, era para ser um operador" + "artimético (+,-,/,*) pertinho de" + this.token.getLexema());
        }
    }
}