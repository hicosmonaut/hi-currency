package hi.cosmonaut.base.domain.usecase

import hi.cosmonaut.base.data.wrapper.Result


interface IBaseSuspendUseCase  {
    suspend operator fun invoke(): Result<Any>
}