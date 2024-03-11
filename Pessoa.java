import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Pessoa {
    String nome, cpf;
    Date dataNascimento;

    Pessoa(String nome, String cpf, Date dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome(){
        return this.nome;
    }

    public String getCpf(){
        return this.cpf;
    }

    public String getDataNascimento() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(this.dataNascimento);
    }


}
