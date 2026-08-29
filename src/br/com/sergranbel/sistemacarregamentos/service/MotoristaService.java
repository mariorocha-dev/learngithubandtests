package br.com.sergranbel.sistemacarregamentos.service;

import br.com.sergranbel.sistemacarregamentos.dao.MotoristaDAO;
import br.com.sergranbel.sistemacarregamentos.model.Motorista;

public class MotoristaService {

    private MotoristaDAO motoristaDAO;

    public MotoristaService() {
        this.motoristaDAO = new MotoristaDAO();
    }

    // ==========================================
    // CADASTRAR
    // ==========================================

    public void cadastrar(Motorista motorista)
            throws Exception {

        validarMotorista(motorista);

        Motorista existente =
                motoristaDAO.buscarPorCpf(
                        motorista.getCpf()
                );

        if (existente != null) {

            throw new IllegalArgumentException(
                    "Já existe um motorista cadastrado com o CPF " +
                    motorista.getCpf() +
                    "."
            );
        }

        motoristaDAO.salvar(motorista);
    }


    // ==========================================
    // BUSCAR POR CPF
    // ==========================================

    public Motorista buscarPorCpf(String cpf)
            throws Exception {

        if (cpf == null || cpf.isBlank()) {

            throw new IllegalArgumentException(
                    "O CPF é obrigatório."
            );
        }

        Motorista motorista =
                motoristaDAO.buscarPorCpf(cpf);

        if (motorista == null) {

            throw new IllegalArgumentException(
                    "Motorista com CPF " +
                    cpf +
                    " não encontrado."
            );
        }

        return motorista;
    }


    // ==========================================
    // DESATIVAR
    // ==========================================

    public void desativar(String cpf)
            throws Exception {

        Motorista motorista =
                buscarPorCpf(cpf);

        if (!motorista.isAtivo()) {

            throw new IllegalArgumentException(
                    "O motorista já está inativo."
            );
        }

        motoristaDAO.desativar(
                motorista.getId()
        );
    }


    // ==========================================
    // VALIDAR
    // ==========================================

    private void validarMotorista(
            Motorista motorista) {

        if (motorista == null) {

            throw new IllegalArgumentException(
                    "O motorista não pode ser nulo."
            );
        }

        if (motorista.getCpf() == null ||
            motorista.getCpf().isBlank()) {

            throw new IllegalArgumentException(
                    "O CPF é obrigatório."
            );
        }

        if (motorista.getNome() == null ||
            motorista.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "O nome do motorista é obrigatório."
            );
        }
    }
    public void alterar(Motorista motorista)
            throws Exception {

        validarMotorista(motorista);

        if (motorista.getId() <= 0) {

            throw new IllegalArgumentException(
                    "O motorista deve possuir um ID válido."
            );
        }

        Motorista atual =
                motoristaDAO.buscarPorId(
                        motorista.getId()
                );

        if (atual == null) {

            throw new IllegalArgumentException(
                    "Motorista não encontrado."
            );
        }

        motoristaDAO.alterar(motorista);
    }
    
}