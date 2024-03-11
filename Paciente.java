import java.util.Date;

public class Paciente extends Pessoa{

    String cartaoSUS;
    Especialidade atendimento;

    Paciente(String nome, String cpf, Date dataNascimento, String cartaoSUS, Especialidade especialidade){
        super(nome, cpf, dataNascimento);
        this.cartaoSUS = cartaoSUS;
        this.atendimento = especialidade;
    }

    public Especialidade getAtendimento() {
        return atendimento;
    }

    public String getCartaoSUS() {
        return cartaoSUS;
    }


}
