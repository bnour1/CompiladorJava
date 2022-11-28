package compiladorl3;

public class ListaCircularNode {

    private int tipo;
    private String var;
    private ListaCircularNode ant;
    private ListaCircularNode prox;

    public ListaCircularNode(int tipo, String var, ListaCircularNode ant, ListaCircularNode prox) {
        this.tipo = tipo;
        this.var = var;
        this.ant = ant;
        this.prox = prox;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListaCircularNode) {
            final ListaCircularNode other = (ListaCircularNode) obj;
            return this.tipo == other.gettipo() && this.var.equals(other.getvar());
        }
        return false;
    }

    public ListaCircularNode getant() {
        return this.ant;
    }

    public void setant(ListaCircularNode ant) {
        this.ant = ant;
    }

    public ListaCircularNode getprox() {
        return this.prox;
    }

    public void setprox(ListaCircularNode prox) {
        this.prox = prox;
    }

    public int gettipo() {
        return tipo;
    }

    public void settipo(int tipo) {
        this.tipo = tipo;
    }

    public String getvar() {
        return var;
    }

    public void setvar(String var) {
        this.var = var;
    }

}