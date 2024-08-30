//
//  BirdPage.swift
//  iosApp
//
//  Created by Jitendra Prajapati on 16/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Kingfisher

struct BirdsPage: View {
    @StateObject var viewModel = BirdViewModel(repository: BirdRepository())
    
   
    var body: some View {
        ScrollView {
            VStack(alignment: .center ) {
                HStack {
                    ForEach(viewModel.uiState.categories, id: \.self) { category in
                        Button(action: {
                            viewModel.selectCategory(category: category)
                        }) {
                            HStack {
                                Text(category)
                                    .frame(minWidth: 0, maxWidth: .infinity)
                                    .padding(5)
                                
                            }
                        }
                        .padding(5)
                        .background(Color.yellow)
                    }
                }
                
                if !viewModel.uiState.selectedImages.isEmpty {
                    LazyVGrid(columns: [GridItem(.flexible(), spacing: 5), GridItem(.flexible(), spacing: 5)], spacing: 5, content: {
                        ForEach(viewModel.uiState.selectedImages, id: \.self) { image in
                            BirdImageCell(image: image)
                        }
                    })
                    .padding(5)
                }
            }.background(Color.blue)
                .frame(maxHeight: .infinity)
        }
    }
}


struct BirdImageCell: View {
    let image: BirdImage

    var body: some View {
        KFImage.url(URL(string: "https://sebastianaigner.github.io/demo-image-api/\(image.path)"))
     .resizable()
     .aspectRatio(1, contentMode: .fill)
     }
    
    
}
 
