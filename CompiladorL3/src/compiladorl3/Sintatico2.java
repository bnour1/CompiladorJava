package compiladorl3;

public class Sintatico2 {

    private Lexico lexico;
    private Token token;
    
    public Sintatico2(Lexico lexico) {
        this.lexico= lexico;
    }
    
    // Programa
    public void P() {

        this.token= this.lexico.nextToken();
        if(!token.getLexema().equals("public")) {
            throw new RuntimeException("Main incompleto");
        }
        
        this.token=this.lexico.nextToken();
        if(!token.getLexema().equals("main")) {
            throw new RuntimeException("Main incompleto");
        }
        
        this.token=this.lexico.nextToken();
        if(!token.getLexema().equals("(")) {
            throw new RuntimeException("Erro! Era esperado um (");
        }
        
        this.token= this.lexico.nextToken();
        if(!token.getLexema().equals(")")) {
            throw new RuntimeException(") não foi fechado");
        }
        
        this.token= this.lexico.nextToken();
        this.B();
        if(this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("Código compilado com sucesso");
        }
        else {
            throw new RuntimeException("Erro, o programa está quase finalizado");
        }
    }
    
    // Bloco 
    private void B() {

        if(!this.token.getLexema().equals("{")){
            throw new RuntimeException("Erro! Era esperado uma {"+ this.token.getLexema());
        }

        this.token= this.lexico.nextToken();
        this.CS();
        if(!this.token.getLexema().equals("}")){
            throw new RuntimeException("} não foi fechado"+ this.token.getLexema());
        }
        this.token= this.lexico.nextToken();
    }
    
    private void CS() {
        
        if((this.token.getTipo() == Token.TIPO_IDENTIFICADOR) || this.token.getLexema().equals("int") || this.token.getLexema().equals("float") || this.token.getLexema().equals("char")) {
            this.C();
            this.CS();
        }
    }
    
    private void C() {
        
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR) {
            this.A();
        }
        else if(this.token.getLexema().equals("int") || this.token.getLexema().equals("float") || this.token.getLexema().equals("char")) { 
            this.DV();
        }
        else {
            throw new RuntimeException("Erro, esperava-se que você declarasse um comando próximo de: "+ this.token.getLexema());
        }
    }
    
    // Declarar variável
    private void DV() {

        if(!(this.token.getLexema().equals("int") || this.token.getLexema().equals("float") || this.token.getLexema().equals("char"))){
            throw new RuntimeException("Erro! Declaração de variável incorreta"+ this.token.getLexema());
        }

        this.token= this.lexico.nextToken();
        if(this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Erro! Declaração de variável incorreta"+ this.token.getLexema());
        }

        this.token = this.lexico.nextToken();
        if(!this.token.getLexema().equalsIgnoreCase(";")) {
            throw new RuntimeException("Erro! Declaração de variável incorreta"+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }
    
    // Atribuição
    private void A() {
        
        if(this.token.getTipo() != Token.TIPO_IDENTIFICADOR){
            throw new RuntimeException("Erro! Atribuição inválida"+ this.token.getLexema());
        }
        
        this.token = this.lexico.nextToken();
        if(this.token.getTipo() != Token.TIPO_OPERADOR_ATRIBUICAO){
            throw new RuntimeException("Erro! Atribuição inválida"+ this.token.getLexema());
        }
        
        this.token = this.lexico.nextToken();
        this.E();
        if(!this.token.getLexema().equals(";")){
            throw new RuntimeException("Erro! Atribuição inválida"+ this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }
      
    private void E() {

        this.T();
        this.El();
    }
    
    private void El() {

        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.El();
        }    
    }
    
    private void T() {

        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO || this.token.getTipo() == Token.TIPO_REAL || this.token.getTipo() == Token.TIPO_CHAR) {
            this.token=this.lexico.nextToken();
        }
        else {
            throw new RuntimeException("Erro! Era para ser um identificador"+ this.token.getLexema());
        }        
    }
    
    private void OP() {
        
        if(this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.token=this.lexico.nextToken();
        }
        else{
            throw new RuntimeException("Erro! Era para ser um operador"+ this.token.getLexema());
        }      
    }
}