package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.VeiculoDTO;
import br.com.ada.testeautomatizado.exception.PlacaInvalidaException;
import br.com.ada.testeautomatizado.exception.VeiculoNaoEncontradoException;
import br.com.ada.testeautomatizado.model.Veiculo;
import br.com.ada.testeautomatizado.repository.VeiculoRepository;
import br.com.ada.testeautomatizado.util.Response;
import br.com.ada.testeautomatizado.util.ValidacaoPlaca;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ValidacaoPlaca validacaoPlaca;

    public ResponseEntity<Response<VeiculoDTO>> cadastrar(VeiculoDTO veiculoDTO) {

        try {
            validacaoPlaca.isPlacaValida(veiculoDTO.getPlaca());

            Veiculo veiculo = criarVeiculo(veiculoDTO);
            veiculoRepository.save(veiculo);

            Response<VeiculoDTO> response = new Response<>("Sucesso", criarVeiculoDTO(veiculo));

            return ResponseEntity.ok(response);

        } catch (PlacaInvalidaException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new Response<>("Placa invalida!", null));

        }
    }

    public ResponseEntity<Response<Boolean>> deletarVeiculoPelaPlaca(String placa) {

        try{

            Optional<Veiculo> optionalVeiculo = buscarVeiculoPelaPlaca(placa);

            Veiculo veiculo = optionalVeiculo.get();
            veiculoRepository.delete(veiculo);

            Response<Boolean> response = new Response<>("Sucesso", true);
            return ResponseEntity.ok(response);

        }catch (VeiculoNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new Response<>("Placa invalida!", null));
        }

    }

    public ResponseEntity<Response<VeiculoDTO>> atualizar(VeiculoDTO veiculoDTO) {
        try{

            Optional<Veiculo> optionalVeiculo = buscarVeiculoPelaPlaca(veiculoDTO.getPlaca());

            Veiculo veiculo = optionalVeiculo.get();
            veiculo.setPlaca(veiculoDTO.getPlaca());
            veiculo.setModelo(veiculoDTO.getModelo());
            veiculo.setMarca(veiculoDTO.getMarca());
            veiculo.setDisponivel(veiculoDTO.getDisponivel());
            veiculo.setDataFabricacao(veiculoDTO.getDataFabricacao());
            Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

            Response<VeiculoDTO> response = new Response<>("Sucesso", criarVeiculoDTO(veiculoSalvo));
            return ResponseEntity.ok(response);

        }catch (VeiculoNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Response<>("Placa invalida!", null));
        }
    }

    public ResponseEntity<Response<List<VeiculoDTO>>> listarTodos() {
        List<VeiculoDTO> veiculos = veiculoRepository.findAll().stream().map(this::criarVeiculoDTO)
                .collect(Collectors.toList());

        Response<List<VeiculoDTO>> response = new Response<>();
        response.setMessage("Sucesso");
        response.setDetail(veiculos);
        return ResponseEntity.ok(response);
    }

    private Optional<Veiculo> buscarVeiculoPelaPlaca(String placa) {
        Optional<Veiculo> optionalVeiculo = this.veiculoRepository.findByPlaca(placa);
        if (!optionalVeiculo.isPresent()) throw new VeiculoNaoEncontradoException();
        return optionalVeiculo;
    }

    private Veiculo criarVeiculo(VeiculoDTO veiculoDTO){
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setMarca(veiculoDTO.getMarca());
        veiculo.setDisponivel(veiculoDTO.getDisponivel());
        veiculo.setDataFabricacao(veiculoDTO.getDataFabricacao());
        return veiculo;
    }

    private VeiculoDTO criarVeiculoDTO(Veiculo veiculo){
        return VeiculoDTO.builder().placa(veiculo.getPlaca())
                .marca(veiculo.getMarca())
                .modelo(veiculo.getModelo())
                .disponivel(veiculo.getDisponivel())
                .dataFabricacao(veiculo.getDataFabricacao())
                .build();
    }


}

