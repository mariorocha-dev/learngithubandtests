package br.com.sergranbel.sistemacarregamentos.service;

import br.com.sergranbel.sistemacarregamentos.dao.CaminhaoDAO;
import br.com.sergranbel.sistemacarregamentos.model.Caminhao;

public class CaminhaoService {

    private CaminhaoDAO caminhaoDAO;

    public CaminhaoService() {
        this.caminhaoDAO = new CaminhaoDAO();
    }


    // ==========================================
    // CADASTRAR
    // ==========================================

    public void cadastrar(Caminhao caminhao)
            throws Exception {

        validarCaminhao(caminhao);

        Caminhao existente =
                caminhaoDAO.buscarPorPlaca(
                        caminhao.getPlaca()
                );

        if (existente != null) {

            throw new IllegalArgumentException(
                    "Já existe um caminhão cadastrado com a placa " +
                    caminhao.getPlaca() +
                    "."
            );
        }

        caminhaoDAO.salvar(caminhao);
    }


    // ==========================================
    // BUSCAR POR PLACA
    // ==========================================

    public Caminhao buscarPorPlaca(String placa)
            throws Exception {

        if (placa == null || placa.isBlank()) {

            throw new IllegalArgumentException(
                    "A placa é obrigatória."
            );
        }

        Caminhao caminhao =
                caminhaoDAO.buscarPorPlaca(placa);

        if (caminhao == null) {

            throw new IllegalArgumentException(
                    "Caminhão com placa " +
                    placa +
                    " não encontrado."
            );
        }

        return caminhao;
    }


    // ==========================================
    // DESATIVAR
    // ==========================================

    public void desativar(String placa)
            throws Exception {

        Caminhao caminhao =
                buscarPorPlaca(placa);

        if (!caminhao.isAtivo()) {

            throw new IllegalArgumentException(
                    "O caminhão já está inativo."
            );
        }

        caminhaoDAO.desativar(
                caminhao.getId()
        );
    }


    // ==========================================
    // VALIDAR
    // ==========================================

    private void validarCaminhao(
            Caminhao caminhao) {

        if (caminhao == null) {

            throw new IllegalArgumentException(
                    "O caminhão não pode ser nulo."
            );
        }

        if (caminhao.getPlaca() == null ||
            caminhao.getPlaca().isBlank()) {

            throw new IllegalArgumentException(
                    "A placa do caminhão é obrigatória."
            );
        }

        if (caminhao.getModelo() == null ||
            caminhao.getModelo().isBlank()) {

            throw new IllegalArgumentException(
                    "O modelo do caminhão é obrigatório."
            );
        }
    }
 // ==========================================
 // ALTERAR
 // ==========================================

 public void alterar(Caminhao caminhao)
         throws Exception {

     validarCaminhao(caminhao);

     if (caminhao.getId() <= 0) {

         throw new IllegalArgumentException(
                 "O ID do caminhão é inválido."
         );
     }

     Caminhao existente =
             buscarPorPlaca(caminhao.getPlaca());

     if (existente.getId() != caminhao.getId()) {

         throw new IllegalArgumentException(
                 "Já existe outro caminhão cadastrado com a placa " +
                 caminhao.getPlaca() +
                 "."
         );
     }

     caminhaoDAO.alterar(caminhao);
 }
}