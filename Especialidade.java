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
    public static void listarMedicos(){
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println("- Nome do Médico: " + medicos.get(i).getNome());
            System.out.println("\tCRM: " + medicos.get(i).getCRM());
            System.out.println("\tSalário: " + medicos.get(i).getSalario());
            System.out.println("\tEspecialidade: "+ medicos.get(i).getEspecialidade().getNome());
            System.out.println("\tCPF: " + medicos.get(i).getCpf());
        }
    }
    public static void addMedico(Medico medico){

    //    Optional<Medico> x = medicos.stream()
    //            .filter(m -> m.getEspecialidade().getNome().equals(medico.getEspecialidade().getNome()))
    //            .findFirst();

        Optional<Medico> medicoEncontrado = medicos.stream()
                .filter(m -> m.getCRM().equals(medico.getCRM()))
                .findFirst();


        if(medicoEncontrado.isPresent()){
            System.out.println("CRM já cadastrado");
        }else{
            medicos.add(medico);
        }
    }
}
