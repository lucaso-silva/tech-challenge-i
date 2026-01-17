package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest;

import java.net.URI;
import java.util.List;

import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import jakarta.validation.Valid;

@Tag(name = "Users",
        description = "Gerenciamento de Usuários (cliente e dono de restaurante)")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ForUserService forUserService;

    public UserController(ForUserService forUserService) {
        this.forUserService = forUserService;
    }

    @Operation(summary = "Listar usuários cadastrados",
            description = "Retorna a lista de usuários cadastrados. Permite filtrar os resultados pelo nome do usuário (opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários cadastrados", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Nao foi encontrado usuário com o nome informado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(
            @Parameter(description = "Nome de usuário para filtro na listagem (opcional)")
            @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(
                forUserService.findByNameLike(name)
                        .stream().map(UserResponseDTO::toDTO).toList());
    }

    @Operation(summary = "Buscar usuário por login",
            description = "Retorna os dados detalhados de um usuário com base no login informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes usuário", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário nao encontrado", content = @Content)
    })
    @GetMapping("/{login}")
    public ResponseEntity<UserDetailResponseDTO> fetchUserByLogin(
            @PathVariable @Parameter(description = "Login do usuário cadastrado") String login) {

        User userByLogin = forUserService.findByLogin(login);
        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(userByLogin));

    }

    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário no sistema com base nos dados informados",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados obrigatórios para criação de um novo usuário (nome, email, login, senha, tipo de usuário e endereço)", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {

        User user = forUserService.create(requestDTO.toDomain());
        URI uri = URI.create("/v1/user/" + user.getLogin());
        return ResponseEntity.created(uri).body(UserResponseDTO.toDTO(user));

    }

    @Operation(summary = "Atualizar dados do usuário",
            description = "Atualiza os dados cadastrais de um usuário previamente registrado",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessário para atualização dos dados cadastrais do usuário", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário nao encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable @Parameter(description = "ID do usuário a ser atualizado") Long id,
                                                            @Valid @RequestBody UpdateUserRequestDTO requestDTO) {
        User updatedUser = forUserService.updateUser(id, requestDTO.toCommand());

        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(updatedUser));
    }

    @Operation(summary = "Atualizar senha do usuário",
            description = "Atualiza a senha do usuário mediante validação da senha atual informada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais necessárias para alteração da senha do usuário (senha atual e nova senha)", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário nao encontrado", content = @Content)
    })
    @PutMapping("/password/{login}")
    public ResponseEntity<Void> changePassword(@PathVariable @Parameter(description = "Login do usuário para alteração de senha") String login,
                                              @Valid @RequestBody ChangePasswordRequestDTO requestDTO) {

        forUserService.changePassword(login, requestDTO.password(), requestDTO.newPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Excluir usuário",
            description = "Exclui um usuário cadastrado com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário nao encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Parameter(description = "ID do usuário a ser excluído") Long id) {
        forUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar login",
            description = "Valida as credenciais do usuário (login e senha) e retorna se a combinação informada é válida ou não",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais do usuário (login e senha) utilizadas para validação de acesso.", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado da validação das credenciais (true para válido, false para inválido)", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidateLoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<ValidateLoginResponseDTO> validateLogin(@Valid @RequestBody ValidateLoginRequestDTO requestDTO){
        boolean isValid = forUserService.validateLogin(requestDTO.login(), requestDTO.password());

        return ResponseEntity.ok(new ValidateLoginResponseDTO(isValid));
    }
}
