import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Especialidade {

    public static ArrayList<Medico> medicos = new ArrayList<>();
    private String nome;
    private double precoConsulta;

    Especialidade(String nome, double preco){
        this.nome = nome;
        this.precoConsulta = preco;
    }

    public double getPrecoConsulta() {
        return precoConsulta;
    }

    public String getNome() {
        return nome;
    }

    public static ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public static int getMedicosSize(){
        return medicos.size();
    }
    public static Medico getMedicoByIndex(int index){
        return medicos.get(index);
    }
    public static void listarMedicos(ArrayList<Medico> medicos){
        if(medicos.isEmpty()){
            System.out.println("\nAinda não há médicos cadastrados.\n");
            return;
        }
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println(i + 1 + ". Médico:");
            System.out.println("\t- Nome do Médico: " + medicos.get(i).getNome());
            System.out.println("\t- CRM: " + medicos.get(i).getCRM());
            System.out.println("\t- Salário basal: R$" + medicos.get(i).getSalario());
            System.out.println("\t- Especialidade: "+ medicos.get(i).getEspecialidade().getNome());
            System.out.println("\t- Salário por Consulta: R$" + medicos.get(i).getEspecialidade().getPrecoConsulta());
            System.out.println("\t- CPF: " + medicos.get(i).getCpf());
        }
    }

    public static ArrayList<Medico> getMedicosByEspecialidade(Especialidade esp){
        ArrayList<Medico> medFiltrados = new ArrayList<>();

        for (int i = 0; i < medicos.size(); i++) {
            Medico medico = medicos.get(i);
            if(medico.getEspecialidade().getNome().equals(esp.getNome())){
                medFiltrados.add(medico);
            }

        }
        return medFiltrados;
    }
    public static boolean addMedico(Medico medico){

    //    Optional<Medico> x = medicos.stream()
    //            .filter(m -> m.getEspecialidade().getNome().equals(medico.getEspecialidade().getNome()))
    //            .findFirst();

        Optional<Medico> medicoEncontrado = medicos.stream()
                .filter(m -> m.getCRM().equals(medico.getCRM()))
                .findFirst();


        if(medicoEncontrado.isPresent()){
            return false;
        }else{
            medicos.add(medico);
            return true;
        }
    }
}
