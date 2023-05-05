package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.VeiculoDTO;
import br.com.ada.testeautomatizado.model.Veiculo;
import br.com.ada.testeautomatizado.repository.VeiculoRepository;
import br.com.ada.testeautomatizado.util.Response;
import br.com.ada.testeautomatizado.util.ValidacaoPlaca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ValidacaoPlaca validacaoPlaca;


    @Test
    @DisplayName("Sucesso ao cadastrar veiculo")
    void cadastrarSucesso(){
        VeiculoDTO veiculoDTO = veiculoDTO();
        doCallRealMethod().when(validacaoPlaca).isPlacaValida(veiculoDTO.getPlaca());

        Response<VeiculoDTO> response = new Response<>("Sucesso", veiculoDTO);

        assertEquals(veiculoService.cadastrar(veiculoDTO), ResponseEntity.ok(response));
    }


    @Test
    @DisplayName("Falha ao cadastrar veiculo por passar placa errada")
    void cadastrarFalha(){
        VeiculoDTO veiculoDTO = veiculoDTO();
        veiculoDTO.setPlaca("XYZ4578");
        doCallRealMethod().when(validacaoPlaca).isPlacaValida(veiculoDTO.getPlaca());

        Response<VeiculoDTO> response = new Response<>("Placa invalida!", null);

        assertEquals(veiculoService.cadastrar(veiculoDTO),
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response));
    }

    @Test
    @DisplayName("Sucesso ao deletar veiculo")
    void deletarSucesso(){

        VeiculoDTO veiculoDTO = veiculoDTO();
        Veiculo veiculo = veiculoBD();

        when(veiculoRepository.findByPlaca(veiculoDTO.getPlaca())).thenReturn(Optional.of(veiculo));

        Response<Boolean> response = new Response<>("Sucesso", true);

        assertEquals(veiculoService.deletarVeiculoPelaPlaca(veiculoDTO.getPlaca()),
                ResponseEntity.ok(response));


    }

    @Test
    @DisplayName("Falha ao deletar veiculo com placa inexistente")
    void deletarFalha(){

        String placa = "XYZ4578";
        Veiculo veiculo = veiculoBD();
        veiculo.setPlaca(placa);

        Optional<Veiculo> optionalVeiculo = Optional.empty();

        when(veiculoRepository.findByPlaca(placa)).thenReturn(optionalVeiculo);

        Response<Boolean> response = new Response<>("Placa invalida!", null);

        assertEquals(veiculoService.deletarVeiculoPelaPlaca(placa),
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response));

    }

    @Test
    @DisplayName("Sucesso ao atualizar veiculo")
    void atualizarSucesso(){

        VeiculoDTO veiculoDTO = veiculoDTO();
        veiculoDTO.setDisponivel(Boolean.FALSE);

        when(veiculoRepository.findByPlaca(anyString())).thenReturn(Optional.of(veiculoBD()));
        when(veiculoRepository.save(veiculoAtualizadoBD())).thenReturn(veiculoAtualizadoBD());

        Response<VeiculoDTO> response = new Response<>("Sucesso", veiculoAtualizadoDTO());

        assertEquals(veiculoService.atualizar(veiculoDTO),ResponseEntity.ok(response));
    }

    @Test
    @DisplayName("Falha ao atualizar veiculo")
    void atualizarFalha(){

        VeiculoDTO veiculoDTO = veiculoDTO();

        Optional<Veiculo> optionalVeiculo = Optional.empty();

        when(veiculoRepository.findByPlaca(anyString())).thenReturn(optionalVeiculo);

        Response<VeiculoDTO> response = new Response<>("Placa invalida!", null);

        assertEquals(veiculoService.atualizar(veiculoDTO),ResponseEntity.status(HttpStatus.NO_CONTENT).body(response));
    }


    @Test
    @DisplayName("Sucesso ao listar todos os veiculos")
    void listarTodosSucesso() {

        when(veiculoRepository.findAll()).thenReturn(List.of(veiculoBD()));

        Response<List<VeiculoDTO>> response = new Response<>("Sucesso", List.of(veiculoDTO()));
        ResponseEntity<Response<List<VeiculoDTO>>> responseMetodoListarTodos = veiculoService.listarTodos();

        assertEquals(responseMetodoListarTodos.getStatusCode(),HttpStatus.OK);
        assertEquals(responseMetodoListarTodos.getBody().getMessage(),response.getMessage());
        assertEquals(responseMetodoListarTodos.getBody().getDetail(),response.getDetail());
    }

    private static VeiculoDTO veiculoDTO(){
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setPlaca("XYZ-4578");
        veiculoDTO.setModelo("F40");
        veiculoDTO.setMarca("FERRARI");
        veiculoDTO.setDisponivel(Boolean.TRUE);
        veiculoDTO.setDataFabricacao(LocalDate.parse("2000-01-01"));
        return veiculoDTO;
    }

    private static VeiculoDTO veiculoAtualizadoDTO(){
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setPlaca("XYZ-4578");
        veiculoDTO.setModelo("F40");
        veiculoDTO.setMarca("FERRARI");
        veiculoDTO.setDisponivel(Boolean.FALSE);
        veiculoDTO.setDataFabricacao(LocalDate.parse("2000-01-01"));
        return veiculoDTO;
    }

    private static Veiculo veiculoBD() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("XYZ-4578");
        veiculo.setModelo("F40");
        veiculo.setMarca("FERRARI");
        veiculo.setDisponivel(Boolean.TRUE);
        veiculo.setDataFabricacao(LocalDate.parse("2000-01-01"));
        return veiculo;
    }

    private static Veiculo veiculoAtualizadoBD() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("XYZ-4578");
        veiculo.setModelo("F40");
        veiculo.setMarca("FERRARI");
        veiculo.setDisponivel(Boolean.FALSE);
        veiculo.setDataFabricacao(LocalDate.parse("2000-01-01"));
        return veiculo;
    }

}