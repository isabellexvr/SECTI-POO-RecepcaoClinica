import java.util.Date;

public class Consulta {
    Especialidade especialidadeConsulta;
    Paciente paciente;
    Medico medico;
    Date dataConsulta;

    boolean consultaConfirmada, pagamento;
    double preco;

    Consulta(Especialidade especialidadeConsulta, Paciente paciente, Medico medico, Date dataConsulta){
        this.especialidadeConsulta = especialidadeConsulta;
        this.paciente = paciente;
        this.medico = medico;
        this.dataConsulta = dataConsulta;
        this.consultaConfirmada = false;

        this.preco = especialidadeConsulta.getPrecoConsulta();
    }

    public Especialidade getEspecialidadeConsulta() {
        return especialidadeConsulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }



    boolean getConsultaConfirmada(){
        return this.consultaConfirmada;
    }

    void reagendarConsulta(Date novaData){
        this.dataConsulta = novaData;
    }

    void cancelarConsulta(){
        this.dataConsulta = null;
        this.consultaConfirmada = false;
    }

    void confirmarConsulta(){
        this.consultaConfirmada = true;
    }

    public double getPreco() {
        return this.preco;
    }

    boolean getPagamento(){
        return this.pagamento;
    }

    void setPagamento(){
        this.pagamento = true;
    }
}
