//
//  ExampleViewModel.swift
//  PlayerK
//

import KMPNativeCoroutinesAsync
import Foundation
import shared

extension ExampleView {
    
    @MainActor
    class ExampleViewModel: ObservableObject {

        private let playerService: PlayerService
        
        @Published
        var playerState: PlayerState = PlayerState.companion.default_
        
        init(playerService: PlayerService = DI.shared.playerService) {
            self.playerService = playerService
        }

        func togglePlay() {
            playerService.togglePlay()
        }

        func forward() {
            playerService.forward()
        }

        func rewind() {
            playerService.rewind()
        }
        
        func observePlayerState() async {
            do {
                let sequence = asyncSequence(for: playerService.playerStateFlow)
                for try await state in sequence {
                    DispatchQueue.main.async {
                        self.playerState = state
                    }
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
}


