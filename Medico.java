import java.util.Date;

public class Medico extends Pessoa{

    String CRM;
    double salario;
    Especialidade especialidade;

    Medico(String nome, String cpf, Date dataNascimento, double salario, String CRM, Especialidade especialidade){
        super(nome, cpf, dataNascimento);
        this.salario = salario;
        this.especialidade = especialidade;
        this.CRM = CRM;
    }

    public String getCRM() {
        return CRM;
    }

    public double getSalario() {
        return salario;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }
}
