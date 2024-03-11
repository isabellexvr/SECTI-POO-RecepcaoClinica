import java.awt.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    static ArrayList<Consulta> consultas = new ArrayList<>();
    static ArrayList<Paciente> pacientes = new ArrayList<>();
    //static ArrayList<Medico> medicos = new ArrayList<>();
    static ArrayList<Especialidade> especialidades = new ArrayList<>();

    public static void main(String[] args) throws ParseException {
        boolean exibirMenu = true;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        //criaçao de uma consulta fake
        Especialidade e1Derma = new Especialidade("Dermatologia", 100);
        Especialidade e2Pedia = new Especialidade("Pediatria", 90);
        Especialidade e3Cardio = new Especialidade("Cardiologia", 150);
        Especialidade e4Neuro = new Especialidade("Neurologia", 150);
        Especialidade e5Psico = new Especialidade("Psicologia", 85);
        especialidades.add(e1Derma);
        especialidades.add(e2Pedia);
        especialidades.add(e3Cardio);
        especialidades.add(e4Neuro);
        especialidades.add(e5Psico);

        Medico m1Derma = new Medico("Mr. Pele", "12345678901", formato.parse("12/01/2000"), 4500.00, "321414",e1Derma);
        Especialidade.addMedico(m1Derma);
        Paciente p1Derma = new Paciente("Sr. Coceira", "11111111111", formato.parse("03/03/1989"), "123456", e1Derma);
        pacientes.add(p1Derma);

        novaConsulta(e1Derma, p1Derma, m1Derma, formato.parse("12/04/2024"));


        System.out.println("\n\t* Policlínica Oxetech *");
        System.out.println("\n\t* Sistema de Agendamento de Consultas *");

        Scanner input = new Scanner(System.in);

        while (true) {
            if (exibirMenu) {
                exibirMenu();
            }

            int escolha = input.nextInt();
            input.nextLine();

            switch (escolha){
                case 1:
                    exibirSubmenu(escolha);
                    int submenuOpcao = input.nextInt();
                    input.nextLine();

                    while (submenuOpcao < 1 || submenuOpcao > 4){
                        System.out.println("\nOpção inválida. Tente novamente.\n");

                        exibirSubmenu(escolha);
                        submenuOpcao = input.nextInt();
                        input.nextLine();
                    }

                    handleSubmenu1(submenuOpcao, input, consultas, formato);
                break;

                case 2:

                    System.out.println("\n* Nova Consulta:\n");

                    Paciente pacienteNovaConsulta = null;

                    System.out.println("\nO paciente já está cadastrado?");
                    System.out.println("1.Sim\n2.Não");
                    int pacienteCadastrado = input.nextInt();
                    input.nextLine();

                    while(pacienteCadastrado != 1 && pacienteCadastrado != 2){
                        System.out.println("\nOpção inválida. Tente novamente.\n");

                        System.out.println("\nO paciente já está cadastrado?");
                        System.out.println("1.Sim\n2.Não");
                        pacienteCadastrado = input.nextInt();
                        input.nextLine();
                    }

                    if(pacienteCadastrado == 1){
                        System.out.println("\nSelecione o índice do paciente da consulta:\n");
                        listarPacientes();
                        int pacienteIndex = input.nextInt() - 1;
                        input.nextLine();

                        pacienteNovaConsulta = pacientes.get(pacienteIndex);

                    }else if(pacienteCadastrado == 2){
                        System.out.println("\nDeseja cadastrar um novo paciente?\n");
                        System.out.println("1.Sim\n2.Não");
                        int cadastrarOuNem = input.nextInt();
                        input.nextLine();

                        while(cadastrarOuNem != 1 && cadastrarOuNem != 2){
                            System.out.println("\nOpção inválida. Tente novamente.\n");
                            System.out.println("\nDeseja cadastrar um novo paciente?\n");
                            System.out.println("1.Sim\n2.Não");
                            cadastrarOuNem = input.nextInt();
                            input.nextLine();
                        }

                        if(cadastrarOuNem == 1){
                            pacienteNovaConsulta = cadastrarPaciente(input, formato);

                        }else if(cadastrarOuNem == 2){
                            continuarOuSair(input);
                        }

                    }

                Medico medicoNovaConsulta;

                    ArrayList<Medico> medicosFiltrados = new ArrayList<>();
                    assert pacienteNovaConsulta != null;
                    medicosFiltrados = Especialidade.getMedicosByEspecialidade(pacienteNovaConsulta.getAtendimento());

                    if(medicosFiltrados.isEmpty()){
                        System.out.println("\nAinda não há médicos cadastrados para essa especialidade.\n\nCancelando agendamento de consulta...\n");
                        continuarOuSair(input);
                        break;
                    }

                    System.out.println("Selecione o médico da consulta:");
                    Especialidade.listarMedicos(medicosFiltrados);
                    int medicoIndex = input.nextInt() - 1;
                    input.nextLine();

                    while(medicoIndex < 0 || medicoIndex > Especialidade.getMedicosSize() - 1){
                        System.out.println("\nMédico não encontrado. Tente novamente\n");
                        System.out.println("Selecione o médico da consulta:");
                        Especialidade.listarMedicos(medicosFiltrados);
                        medicoIndex = input.nextInt() - 1;
                        input.nextLine();
                    }

                    medicoNovaConsulta = Especialidade.getMedicoByIndex(medicoIndex);

                    System.out.println("\nPara quando será agendada a nova consulta?\n");

                    Date dataConsulta = null;
                    boolean formatoValido = false;

                    while (!formatoValido) {
                        System.out.println("Insira a data da consulta (padrão dd/mm/yyyy):");
                        String data = input.nextLine();

                        try {
                            dataConsulta = formato.parse(data);
                            formatoValido = true;
                        } catch (ParseException e) {
                            System.out.println("Formato inválido. Por favor, insira a data no formato dd/mm/yyyy.");
                        }
                    }

                    Consulta novaConsulta = new Consulta(medicoNovaConsulta.getEspecialidade(), pacienteNovaConsulta, medicoNovaConsulta, dataConsulta);
                    consultas.add(novaConsulta);

                    System.out.println("\nConsulta cadastrada com sucesso.\n");

                    break;

                case 3:
                    exibirSubmenu(escolha);

                    int escolhaSub = input.nextInt();
                    input.nextLine();

                    while (escolhaSub <= 0 || escolhaSub > 3){
                        System.out.println("\nOpção inválida. Tente novamente.\n");

                        exibirSubmenu(escolha);

                        escolhaSub = input.nextInt();
                        input.nextLine();
                    }

                    handleSubmenu2(escolhaSub, input ,formato);

                    break;
                case 4:
                    exibirSubmenu(escolha);

                    int escolhaSub4 = input.nextInt();
                    input.nextLine();

                    while (escolhaSub4 != 1){
                        System.out.println("\nOpção inválida. Tente novamente.\n");

                        exibirSubmenu(escolha);

                        escolhaSub4 = input.nextInt();
                        input.nextLine();
                    }

                    handleSubmenu3(escolhaSub4, input, formato);

                    break;
                case 5:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    continuarOuSair(input);
            }
        }
    }

    public static void exibirMenu() {
        System.out.println("\nMenu:");
        System.out.println("(Insira um número para cada funcionalidade)");
        System.out.println("\n1. Listar");
        System.out.println("2. Agendar Consulta");
        System.out.println("3. Editar Consulta");
        System.out.println("4. Gerenciar médicos");
        System.out.println("5. Sair");

    }

    public static void exibirSubmenu(int indexMenu){
        System.out.println("\nSubmenu:");
        switch (indexMenu){
            case 1:
                System.out.println("\n1. Listar pacientes");
                System.out.println("2. Listar médicos");
                System.out.println("3. Listar consultas");
                System.out.println("4. Listas especialidades");
                break;
            case 3:
                System.out.println("\n1. Remarcar consulta");
                System.out.println("2. Cancelar consulta");
                System.out.println("3. Confirmar consulta");
                break;
            case 4:
                System.out.println("1. Cadastrar novo médico");
                //System.out.println("2. Cadastrar novo paciente");
                break;
        }
    }

    public static void handleSubmenu1(int escolha, Scanner input, ArrayList<Consulta> consultas, SimpleDateFormat formato) throws ParseException {

        switch (escolha){
            case 1:
                System.out.println("\n* Listando pacientes... *\n");
                listarPacientes();
                continuarOuSair(input);
                break;

            case 2:
                System.out.println("\n* Listando médicos... *\n");
                Especialidade.listarMedicos(Especialidade.getMedicos());
                continuarOuSair(input);
                break;
            case 3:
                System.out.println("\n* Listando consultas... *\n");
                listarConsultas(consultas, formato);
                //listar medicos da especialidade???
                continuarOuSair(input);
                break;
            case 4:
                System.out.println("\n* Listando especialidades... *\n");
                listarEspecialidades(true);
                continuarOuSair(input);
                break;
        }

    }

    public static void handleSubmenu3(int escolha, Scanner input, SimpleDateFormat formato){
        switch (escolha){
            case 1:
                cadastrarMedico(input, formato);
                break;
            default:
                continuarOuSair(input);
        }
    }
    public static Consulta getConsultaByEspecialidade(Scanner input, SimpleDateFormat formato, String op){
        System.out.println("De qual especialidade é a consulta?\n(insira o número correspondente à especialidade)");
        listarEspecialidades(false);
        int especialidadeIndex = input.nextInt() - 1;
        input.nextLine();

        while (especialidadeIndex < 0 || especialidadeIndex > especialidades.size() - 1){
            System.out.println("\nEspecialidade não encontrada. Tente novamente.\n");

            System.out.println("De qual especialidade é a consulta?\n(insira o número correspondente à especialidade)");
            listarEspecialidades(false);
            especialidadeIndex = input.nextInt() - 1;
            input.nextLine();
        }

        Especialidade esp = especialidades.get(especialidadeIndex);

        //cast de stream pra lista
        List<Consulta> consultasEsp = consultas.stream()
                .filter(c -> c.getEspecialidadeConsulta().getNome().equals(esp.getNome()))
                .toList();

        //cast de lista pra arraylist
        ArrayList<Consulta> consultasArrayList = new ArrayList<>(consultasEsp);

        if(consultasArrayList.isEmpty()){

            return null;
        }

        System.out.println("\nSelecione a consulta que deseja " + op + ": \n");

        listarConsultas(consultasArrayList, formato);

        int consultaIndex = input.nextInt() - 1;
        input.nextLine();

        while(consultaIndex < 0 || consultaIndex > consultasEsp.size() - 1){
            System.out.println("\nConsulta não encontrada. Tente novamente.\n");

            System.out.println("\nSelecione a consulta que deseja " + op + ": \n");
            listarConsultas(consultasArrayList, formato);

            consultaIndex = input.nextInt() - 1;
            input.nextLine();
        }

        Consulta consulta = consultasEsp.get(consultaIndex);
        return consulta;
    }

    public static void handleSubmenu2(int escolha, Scanner input, SimpleDateFormat formato){
        switch (escolha){
            case 1:
                //remarcar consulta
                Consulta consultaRemarcar = getConsultaByEspecialidade(input, formato, "reagendar");

                if(consultaRemarcar == null){
                    System.out.println("\nNão há consultas para essa especialidade.\n");
                    continuarOuSair(input);
                    break;
                }

                System.out.println("\nA consulta selecionada está agendada para " + formato.format(consultaRemarcar.getDataConsulta()) + ". \nDeseja mudar para que dia?");

                Date dataConsulta = null;
                boolean formatoValido = false;

                while (!formatoValido) {
                    System.out.println("\nInsira a data da consulta (padrão dd/mm/yyyy):");
                    String data = input.nextLine();

                    try {
                        dataConsulta = formato.parse(data);
                        formatoValido = true;
                    } catch (ParseException e) {
                        System.out.println("Formato inválido. Por favor, insira a data no formato dd/mm/yyyy.");
                    }
                }

                consultaRemarcar.reagendarConsulta(dataConsulta);

                System.out.println("\nConsulta reagendada para " + formato.format(dataConsulta) + " com sucesso.\n");

                continuarOuSair(input);

                break;

            case 2:
                //cancelar consulta
                Consulta consultaCancelar = getConsultaByEspecialidade(input, formato, "cancelar");

                if(consultaCancelar == null){
                    System.out.println("\nNão há consultas para essa especialidade.\n");
                    continuarOuSair(input);
                    break;
                }

                consultaCancelar.cancelarConsulta();

                System.out.println("\nConsulta cancelada com sucesso.\n");

                //System.out.println("oh papai");
                consultas.remove(consultaCancelar);

                continuarOuSair(input);
                break;
            case 3:
                //confirmar consulta
                Consulta consultaConfirmar = getConsultaByEspecialidade(input, formato, "confirmar");

                if(consultaConfirmar == null){
                    System.out.println("\nNão há consultas para essa especialidade.\n");
                    continuarOuSair(input);
                    break;
                }

                consultaConfirmar.confirmarConsulta();
                System.out.println("\nConsulta confirmada com sucesso.\n");
                continuarOuSair(input);
                break;
        }
    }

    public static int calcularIdade(String dataNascimentoString) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = formato.parse(dataNascimentoString);

        Calendar dataNascimentoCal = Calendar.getInstance();
        dataNascimentoCal.setTime(dataNascimento);

        Calendar hoje = Calendar.getInstance();
        int anoAtual = hoje.get(Calendar.YEAR);
        int mesAtual = hoje.get(Calendar.MONTH);
        int diaAtual = hoje.get(Calendar.DAY_OF_MONTH);

        int anoNascimento = dataNascimentoCal.get(Calendar.YEAR);
        int mesNascimento = dataNascimentoCal.get(Calendar.MONTH);
        int diaNascimento = dataNascimentoCal.get(Calendar.DAY_OF_MONTH);

        int idade = anoAtual - anoNascimento;

        if (mesAtual < mesNascimento || (mesAtual == mesNascimento && diaAtual < diaNascimento)) {
            idade--;
        }

        return idade;
    }

    public static void novaConsulta(Especialidade esp, Paciente pac, Medico med, Date data){

        if( !( esp.getNome().equals(pac.getAtendimento().getNome()) ) || !( esp.getNome().equals(med.getEspecialidade().getNome()))){
            System.out.println("\nAs especialidades não coincidem.");
            return;
        }

        Consulta consulta = new Consulta(esp, pac, med, data);
        consultas.add(consulta);
        System.out.println("\nConsulta criada com sucesso!\nLembre-se de confirmar a consulta!");
    }

    public static void listarPacientes() throws ParseException {

        if(pacientes.isEmpty()){
            System.out.println("\nAinda não há pacientes cadastrados.\n");
            return;
        }
        System.out.println("\n");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + 1 + ". Paciente:");
            System.out.println( "\t- Nome do Paciente: " + pacientes.get(i).getNome());
            System.out.println("\t- Cartão do SUS: " + pacientes.get(i).getCartaoSUS());
            System.out.println("\t- Especialidade do Atendimento: "+ pacientes.get(i).getAtendimento().getNome());
            System.out.println("\t- Idade: " + pacientes.get(i).getDataNascimento());
            System.out.println("\t- CPF: " + pacientes.get(i).getCpf());
        }
    }

    public static void cadastrarMedico(Scanner input, SimpleDateFormat formato){

        System.out.println("\n\t* Cadastro de Médico *\n");
        System.out.println("\nInsira o nome do médico:");
        String nome =  input.nextLine();

        while (nome.length() < 3){
            System.out.println("\nO nome deve ter ao menos 3 dígitos. Tente novamente.\n");

            System.out.println("\nInsira o nome do médico:");
            nome =  input.nextLine();
        }

        System.out.println("\nInsira o cpf do médico:\n(OBS.: O CPF deve ser único.)");
        String cpf = input.nextLine();

        while(cpf.length() < 11 || !isNumeric(cpf)){
            System.out.println("\nFormato de cpf não permitido. Deve possuir 11 dígitos e ser numérico. Tente novamente.\n");
            System.out.println("\nInsira o cpf do paciente:\n(OBS.: O CPF deve ser único.)");
            cpf = input.nextLine();
        }

        String finalCpf = cpf;

        Optional<Medico> medicoCPFRepetido = Especialidade.getMedicos().stream()
                .filter(m -> m.getCpf().equals(finalCpf))
                .findFirst();

        if(medicoCPFRepetido.isPresent()){
            System.out.println("\nO médico com o CPF " + cpf + "já está cadastrado no nome de " + medicoCPFRepetido.get().getNome());
            System.out.println("\nSelecione uma das opções");
            System.out.println("1. Cancelar cadastro");
            System.out.println("2. Digitar o CPF novamente");
           // System.out.println("3. Utilizar o usuário " + medicoCPFRepetido.get().getNome() );
            //System.out.println("1. Sim\n2.Não");
            int cancelarCad = input.nextInt();
            input.nextLine();

            while(cancelarCad != 1 && cancelarCad != 2 && cancelarCad != 3){
                System.out.println("\nOpção inválida. Tente novamente.\n");

                System.out.println("\nSelecione uma das opções");
                System.out.println("1. Cancelar cadastro");
                System.out.println("2. Digitar o CPF novamente");
                cancelarCad = input.nextInt();
                input.nextLine();
            }

            if(cancelarCad == 1){
                continuarOuSair(input);
            }else if(cancelarCad == 2){
                System.out.println("Insira o cpf do médico:");
                cpf = input.nextLine();
            }
        }

        Date dataNascimento = null;
        boolean formatoValido = false;

        while (!formatoValido) {
            System.out.println("\nInsira a data de nascimento do médico (padrão dd/mm/yyyy):");
            String data = input.nextLine();

            try {
                dataNascimento = formato.parse(data);
                formatoValido = true;
            } catch (ParseException e) {
                System.out.println("\nFormato inválido. Por favor, insira a data no formato dd/mm/yyyy.");
            }
        }

        System.out.println("\nSelecione qual será a especialidade do médico:\n");

        listarEspecialidades(true);
        int especialidadeIndex = input.nextInt() - 1;
        input.nextLine();

        while(especialidadeIndex < 0 || especialidadeIndex > especialidades.size() - 1){
            System.out.println("\nEssa especialidade não existe. Tente novamente\n");

            System.out.println("\nSelecione qual será a especialidade do médico:\n");

            listarEspecialidades(true);
            especialidadeIndex = input.nextInt() - 1;
            input.nextLine();
        }

        Especialidade especialidade = especialidades.get(especialidadeIndex);

        System.out.println("\nInsira o CRM do médico: \n");

        String CRM = input.nextLine();
        while (!isNumeric(CRM)){
            System.out.println("\nO CRM deve ser numérico. Tente novamente.\n");
            System.out.println("\nInsira o CRM do médico (Deve ser único): \n");
            CRM = input.nextLine();
        }

        System.out.println("Qual será o salário base do médico?");

        double salario = input.nextDouble();
        input.nextLine();

        while(salario < 1500){
            System.out.println("Salário abaixo do indicado. Tente acima de R$1.500.");
            salario = input.nextDouble();
            input.nextLine();
        }

        //salario base e ganha a mais por consulta

        Medico newMed = new Medico(nome, cpf, dataNascimento, salario, CRM, especialidade);
        boolean status = Especialidade.addMedico(newMed);

        if(!status){
            System.out.println("\nCRM já cadastrado\n");
        }else{
            System.out.println("\n Médico cadastrado com sucesso!\n");
        }
    }

    public static void adicionarNovaEspecialidade(){

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Paciente cadastrarPaciente(Scanner input, SimpleDateFormat formato) throws ParseException {
        System.out.println("\n* Cadastro de Paciente *\n");
        System.out.println("\nInsira o nome do paciente:");
        String nome =  input.nextLine();

        while (nome.length() < 3){
            System.out.println("\nO nome deve ter ao menos 3 dígitos. Tente novamente.\n");

            System.out.println("\nInsira o nome do paciente:");
            nome =  input.nextLine();
        }

        System.out.println("\nInsira o cpf do paciente:\n(OBS.: O CPF deve ser único.)");
        String cpf = input.nextLine();

        while(cpf.length() < 11 || !isNumeric(cpf)){
            System.out.println("\nFormato de cpf não permitido. Deve possuir 11 dígitos e ser numérico. Tente novamente.\n");
            System.out.println("\nInsira o cpf do paciente:\n(OBS.: O CPF deve ser único.)");
            cpf = input.nextLine();
        }

        String finalCpf = cpf;
        Optional<Paciente> pacienteCPFRepetido = pacientes.stream()
                .filter(p -> p.getCpf().equals(finalCpf))
                .findFirst();

        if(pacienteCPFRepetido.isPresent()){
            System.out.println("\nO CPF " + cpf + "já está cadastrado no nome de " + pacienteCPFRepetido.get().getNome());
            System.out.println("\nSelecione uma das opções");
            System.out.println("1. Cancelar cadastro");
            System.out.println("2. Digitar o CPF novamente");
            System.out.println("3. Utilizar o usuário " + pacienteCPFRepetido.get().getNome() );
            //System.out.println("1. Sim\n2.Não");
            int cancelarCad = input.nextInt();
            input.nextLine();

            while(cancelarCad != 1 && cancelarCad != 2 && cancelarCad != 3){
                System.out.println("\nOpção inválida. Tente novamente.\n");

                System.out.println("\nSelecione uma das opções");
                System.out.println("1. Cancelar cadastro");
                System.out.println("2. Digitar o CPF novamente");
                System.out.println("3. Utilizar o usuário " + pacienteCPFRepetido.get().getNome() );
                //System.out.println("1. Sim\n2.Não");
                cancelarCad = input.nextInt();
                input.nextLine();
            }

            if(cancelarCad == 1){
                continuarOuSair(input);
            }else if(cancelarCad == 2){
                System.out.println("Insira o cpf do paciente:");
                cpf = input.nextLine();
            }else if(cancelarCad == 3){
                return pacienteCPFRepetido.get();
            }
        }

        System.out.println("\nInsira o número do Cartão do SUS do paciente:");
        String cartaoSUS = input.nextLine();

        while (!isNumeric(cartaoSUS)){
            System.out.println("\nDeve ser numérico. Tente novamente.\n");

            System.out.println("\nInsira o número do Cartão do SUS do paciente:");
            cartaoSUS = input.nextLine();
        }

        String finalCartaoSUS = cartaoSUS;
        Optional<Paciente> pacienteCartaoSUSRepetido = pacientes.stream()
                .filter(p -> p.getCartaoSUS().equals(finalCartaoSUS))
                .findFirst();

        if(pacienteCartaoSUSRepetido.isPresent()){
            System.out.println("\nO Cartão SUS " + cartaoSUS + "já está cadastrado no nome de " + pacienteCartaoSUSRepetido.get().getNome());
            System.out.println("\nSelecione uma das opções");
            System.out.println("1. Cancelar cadastro");
            System.out.println("2. Digitar o Cartão SUS novamente");
            System.out.println("3. Utilizar o usuário " + pacienteCartaoSUSRepetido.get().getNome() );
            int cancelarCad = input.nextInt();
            input.nextLine();

            while(cancelarCad != 1 && cancelarCad != 2 && cancelarCad != 3){
                System.out.println("\nSelecione uma das opções");
                System.out.println("1. Cancelar cadastro");
                System.out.println("2. Digitar o Cartão SUS novamente");
                System.out.println("3. Utilizar o usuário " + pacienteCartaoSUSRepetido.get().getNome() );
                cancelarCad = input.nextInt();
                input.nextLine();
            }

            if(cancelarCad == 1){
                continuarOuSair(input);
            }else if(cancelarCad == 2){
                System.out.println("\nInsira o número do Cartão do SUS do paciente:");
                cartaoSUS = input.nextLine();
            }else if(cancelarCad == 3){
                return pacienteCartaoSUSRepetido.get();
            }
        }

        Date dataNascimento = null;
        boolean formatoValido = false;

        while (!formatoValido) {
            System.out.println("\nInsira a data de nascimento do paciente (padrão dd/mm/yyyy):");
            String data = input.nextLine();

            try {
                dataNascimento = formato.parse(data);
                formatoValido = true;
            } catch (ParseException e) {
                System.out.println("\nFormato inválido. Por favor, insira a data no formato dd/mm/yyyy.");
            }
        }

        System.out.println("\nSelecione para qual especialidade o paciente será atendido:\n");

        listarEspecialidades(true);
        int especialidadeIndex = input.nextInt() - 1;
        input.nextLine();

        while(especialidadeIndex < 0 || especialidadeIndex > especialidades.size() - 1){
            System.out.println("\nEssa especialidade não existe. Tente novamente\n");

            System.out.println("\nSelecione para qual especialidade o paciente será atendido:\n");

            listarEspecialidades(true);
            especialidadeIndex = input.nextInt() - 1;
            input.nextLine();
        }

        Especialidade especialidade = especialidades.get(especialidadeIndex);

        System.out.println("\n Paciente cadastrado com sucesso!\n");

        Paciente newPac = new Paciente(nome, cpf, dataNascimento, cartaoSUS, especialidade);
        pacientes.add(newPac);

        return newPac;

    }
    public static void listarConsultas(ArrayList<Consulta> consultas, SimpleDateFormat formato){

        if(consultas.isEmpty()){
            System.out.println("\nAinda não há consultas cadastradas.\n");
            return;
        }
        for (int i = 0; i < consultas.size(); i++) {

            Consulta consulta = consultas.get(i);
            Medico medico = consulta.getMedico();
            Paciente paciente = consulta.getPaciente();
            Especialidade especialidade = consulta.getEspecialidadeConsulta();

            System.out.println(i + 1 + ". Consulta de " + especialidade.getNome());
            System.out.println("\tMédico: " + medico.getNome() + "(CRM: "+ medico.getCRM()+ ")");
            System.out.println("\tPaciente: " + paciente.getNome() + "(Cartão do SUS: " + paciente.getCartaoSUS()+ ")");
            System.out.println("\tData: " + formato.format(consulta.getDataConsulta()));
            System.out.println("\tValor da Consulta: " + consulta.getPreco());
            System.out.println("\tStatus Geral: " + (consulta.getConsultaConfirmada() ? "Consulta Confirmada." : "Consulta ainda não confirmada"));
            System.out.println("\tStatus do Pagamento: " + (consulta.getPagamento() ? "Pagamento Confirmado." : "Ainda não pago."));

        }

    }

    public static void listarEspecialidades(boolean mostrarPreco){

        for (int i = 0; i < especialidades.size(); i++) {

            Especialidade especialidade = especialidades.get(i);

            System.out.println(i + 1 + ". " +especialidade.getNome());
            if(mostrarPreco){
                System.out.println("\tPreço por Consulta: R$" + especialidade.getPrecoConsulta());
            }
            //System.out.println("\t");
        }
    }

    public static void continuarOuSair(Scanner input) {
        System.out.println("\n");
        System.out.println("1. Voltar ao menu principal");
        System.out.println("2. Sair do programa");

        int escolha = input.nextInt();
        input.nextLine();

        switch (escolha) {
            case 1:
                System.out.println("\nVoltando ao menu principal...\n");
                break;
            case 2:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Voltando ao menu principal...\n");
        }
    }

}
