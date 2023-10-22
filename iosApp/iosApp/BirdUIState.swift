//
//  BirdUIState.swift
//  iosApp
//
//  Created by Jitendra Prajapati on 16/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

struct BirdsUiState {
    var images: [BirdImage] = [BirdImage]()
    let selectedCategory: String?
    
    var categories: Array<String> {
        Array(Set(images.map { $0.category }))
    }
    
    var selectedImages: [BirdImage] {
        images.filter { $0.category == selectedCategory }
    }
}
