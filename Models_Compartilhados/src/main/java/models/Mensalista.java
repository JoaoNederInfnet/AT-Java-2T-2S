package models;

public class Mensalista
{
    /*/ ------------------------------- ATRIBUTOS ------------------------------- /*/
    //1) Matrícula
    private int matricula;
    //--------------------------------------------/------------------------------------------

    //2) Nome
    private String nome;
    //========================================================

    /*/ ------------------------------- PROPRIEDADES ------------------------------- /*/
    //1) Matrícula
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    //--------------------------------------------/------------------------------------------
    //2) Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    //========================================================

    /*/ ------------------------------- CONSTRUTORES ------------------------------- /*/
    //1)Cheio -> Mensalista já registrado
    public Mensalista(int matricula, String nome)
    {
        setMatricula(matricula);
        setNome(nome);
    }
    //--------------------------------------------/------------------------------------------
    //2)Incompleto -> Mensalista não registrado
    public Mensalista(String nome)
    {
        setNome(nome);
    }
    //--------------------------------------------/------------------------------------------
    //3)Vazio
    public Mensalista() {}
}