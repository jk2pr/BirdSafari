//
//  BirdViewModel.swift
//  iosApp
//
//  Created by Jitendra Prajapati on 16/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

class BirdViewModel: ObservableObject {
    private let repository: BirdRepository
    private var cancellables = Set<AnyCancellable>()
    
    
    @Published private var _uiState: BirdsUiState = BirdsUiState(selectedCategory: nil)
    var uiState: BirdsUiState {_uiState }
    
    init(repository: BirdRepository) {
        self.repository = repository
        updateImages()
    }
    
    func selectCategory(category: String) {
        _uiState = BirdsUiState(images: _uiState.images, selectedCategory: category)
         
    }
    
    func updateImages() {
        repository.getImages { [weak self] images, error in
            guard let self = self else { return }
            _uiState = BirdsUiState(
                images: images! as [BirdImage],
                selectedCategory: self._uiState.selectedCategory
            )
            
        }
        
      }
    
    deinit {
        repository.close()
    }
}
