package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.api;

import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users",
        description = "Gerenciamento de Usuários (cliente e dono de restaurante)")
@RequestMapping("/v1/user")
public interface UserApi {

    @Operation(summary = "Listar usuários cadastrados",
            description = "Retorna a lista de usuários cadastrados. Permite filtrar os resultados pelo nome do usuário (opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários cadastrados", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))})
    })
    @GetMapping
    ResponseEntity<List<UserResponseDTO>> findAll(
            @Parameter(description = "Nome de usuário para filtro na listagem (opcional)")
            @RequestParam(name = "name", required = false) String name
    );

    @Operation(summary = "Buscar usuário por login",
            description = "Retorna os dados detalhados de um usuário com base no login informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes usuário", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{login}")
    ResponseEntity<UserDetailResponseDTO> fetchUserByLogin(
            @PathVariable @Parameter(description = "Login do usuário cadastrado") String login
    );

    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário no sistema com base nos dados informados",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados obrigatórios para criação de um novo usuário (nome, email, login, senha, tipo de usuário e endereço)", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos", content = @Content)
    })
    @PostMapping
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO);

    @Operation(summary = "Atualizar dados do usuário",
            description = "Atualiza os dados cadastrais de um usuário previamente registrado",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessários para atualização dos dados cadastrais do usuário", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PutMapping("/{login}")
    ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable @Parameter(description = "Login do usuário a ser atualizado") String login,
                                                     @Valid @RequestBody UpdateUserRequestDTO requestDTO);

    @Operation(summary = "Atualizar senha do usuário",
            description = "Atualiza a senha do usuário mediante validação da senha atual informada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais necessárias para alteração da senha do usuário (senha atual e nova senha)", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PutMapping("/password/{login}")
    ResponseEntity<Void> changePassword(@PathVariable @Parameter(description = "Login do usuário para alteração de senha") String login,
                                               @Valid @RequestBody ChangePasswordRequestDTO requestDTO);

    @Operation(summary = "Excluir usuário",
            description = "Exclui um usuário cadastrado com base no login informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{login}")
    ResponseEntity<Void> deleteUser(@PathVariable @Parameter(description = "Login do usuário a ser excluído") String login);

}
