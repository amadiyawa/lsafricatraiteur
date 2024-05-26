package com.amadiyawa.feature_delivery.domain.usecase

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.domain.model.Menu
import com.amadiyawa.feature_delivery.domain.repository.MenuRepository

internal class GetMenuListUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(): Result<List<Menu>> {
        return menuRepository.getAllMenus()
    }
}