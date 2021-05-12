package br.com.zup.edu

import javax.validation.constraints.NotBlank

data class StatusCpfResponse(@field:NotBlank val cpf: String?, @field:NotBlank val mensagem:String?) {
    constructor(response: ValidacpfResponse?) : this(
        cpf = response?.cpf,
        mensagem = response?.mensagem
    ){

    }

}
