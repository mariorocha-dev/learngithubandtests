package br.com.sergranbel.sistemacarregamentos.service;

import br.com.sergranbel.sistemacarregamentos.dao.CarretaDAO;
import br.com.sergranbel.sistemacarregamentos.model.Carreta;

public class CarretaService {

    private CarretaDAO carretaDAO;

    public CarretaService() {
        this.carretaDAO = new CarretaDAO();
    }


    // ==========================================
    // CADASTRAR
    // ==========================================

    public void cadastrar(Carreta carreta)
            throws Exception {

        validarCarreta(carreta);

        Carreta existente =
                carretaDAO.buscarPorPlaca(
                        carreta.getPlaca()
                );

        if (existente != null) {

            throw new IllegalArgumentException(
                    "Já existe uma carreta cadastrada com a placa " +
                    carreta.getPlaca() +
                    "."
            );
        }

        carretaDAO.salvar(carreta);
    }


    // ==========================================
    // BUSCAR POR PLACA
    // ==========================================

    public Carreta buscarPorPlaca(String placa)
            throws Exception {

        if (placa == null || placa.isBlank()) {

            throw new IllegalArgumentException(
                    "A placa é obrigatória."
            );
        }

        Carreta carreta =
                carretaDAO.buscarPorPlaca(placa);

        if (carreta == null) {

            throw new IllegalArgumentException(
                    "Carreta com placa " +
                    placa +
                    " não encontrada."
            );
        }

        return carreta;
    }


    // ==========================================
    // DESATIVAR
    // ==========================================

    public void desativar(String placa)
            throws Exception {

        Carreta carreta =
                buscarPorPlaca(placa);

        if (!carreta.isAtivo()) {

            throw new IllegalArgumentException(
                    "A carreta já está inativa."
            );
        }

        carretaDAO.desativar(
                carreta.getId()
        );
    }


    // ==========================================
    // VALIDAR
    // ==========================================

    private void validarCarreta(
            Carreta carreta) {

        if (carreta == null) {

            throw new IllegalArgumentException(
                    "A carreta não pode ser nula."
            );
        }

        if (carreta.getPlaca() == null ||
            carreta.getPlaca().isBlank()) {

            throw new IllegalArgumentException(
                    "A placa da carreta é obrigatória."
            );
        }
    }
 // ==========================================
 // ALTERAR
 // ==========================================

 // ==========================================
 // ALTERAR
 // ==========================================

 public void alterar(Carreta carreta)
         throws Exception {

     validarCarreta(carreta);

     if (carreta.getId() <= 0) {

         throw new IllegalArgumentException(
                 "A carreta não possui um ID válido."
         );
     }

     Carreta existente =
             carretaDAO.buscarPorId(
                     carreta.getId()
             );

     if (existente == null) {

         throw new IllegalArgumentException(
                 "Carreta não encontrada."
         );
     }

     // Verifica se a nova placa já pertence
     // a outra carreta
     Carreta outra =
             carretaDAO.buscarPorPlaca(
                     carreta.getPlaca()
             );

     if (outra != null &&
         outra.getId() != carreta.getId()) {

         throw new IllegalArgumentException(
                 "Já existe outra carreta cadastrada " +
                 "com a placa " +
                 carreta.getPlaca() +
                 "."
         );
     }

     carretaDAO.alterar(carreta);
 }
}