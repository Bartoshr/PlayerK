//
//  ExampleView.swift
//  PlayerK
//

import SwiftUI

struct ExampleView: View {
    
    @ObservedObject var viewModel: ExampleViewModel = ExampleViewModel()
    
    var body: some View {
        VStack(spacing: 10) {
            Text(viewModel.playerState.song.title)
                .font(.title)
            Text(viewModel.playerState.song.artist)
                .font(.body)
            AsyncImage(url: URL(string: viewModel.playerState.song.coverUrl)
            ) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .frame(maxWidth: 300, alignment: .center)
            } placeholder: {
                ProgressView()
            }
            HStack(spacing: 20) {
                Text(viewModel.playerState.position.formattedCurrentTime)
                    .font(.title)
                Spacer()
                Text(viewModel.playerState.position.formattedTotalTime)
                    .font(.title)
            }.padding(25)
            HStack(spacing: 20) {
                Button(action: {
                    viewModel.rewind()
                }) {
                    Image(systemName: "gobackward.5")
                        .font(.title)
                }
                Button(action: {
                    viewModel.togglePlay()
                }) {
                    switch(viewModel.playerState.status) {
                    case .playing:
                        Image(systemName: "pause.fill")
                            .font(.system(size: 60))
                    case .idle:
                        Image(systemName: "play.fill")
                            .font(.system(size: 60))
                    default:
                        ProgressView()
                    }
                }
                Button(action: {
                    viewModel.forward()
                }) {
                    Image(systemName: "goforward.5")
                        .font(.title)
                }
            }
        }
        .task {
            await viewModel.observePlayerState()
        }
        .padding()
    }
}
