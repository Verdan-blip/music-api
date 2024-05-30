package org.muztache.api.service;

import lombok.RequiredArgsConstructor;
import org.muztache.api.coverter.PlaylistToPlaylistResponseConverter;
import org.muztache.api.coverter.TrackToTrackResponseConverter;
import org.muztache.api.coverter.UserToUserResponseConverter;
import org.muztache.api.dto.playlist.response.PlaylistResponse;
import org.muztache.api.dto.search.SearchResponse;
import org.muztache.api.dto.track.response.TrackResponse;
import org.muztache.api.dto.user.response.UserResponse;
import org.muztache.api.repository.PlaylistRepository;
import org.muztache.api.repository.TrackRepository;
import org.muztache.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class SearchService {

    private final TrackRepository trackRepository;

    private final UserRepository userRepository;

    private final PlaylistRepository playlistRepository;


    private final TrackToTrackResponseConverter trackToTrackResponseConverter;

    private final PlaylistToPlaylistResponseConverter playlistToPlaylistResponseConverter;

    private final UserToUserResponseConverter userToUserResponseConverter;

    public SearchResponse searchByKeywords(String[] keys) {

        final Set<TrackResponse> foundTracks = new HashSet<>();
        final Set<PlaylistResponse> foundPlaylists = new HashSet<>();
        final Set<UserResponse> foundUsers = new HashSet<>();

        for (String key : keys) {
            foundTracks.addAll(
                    trackRepository
                            .findAllByTitleContainingIgnoreCase(key)
                            .stream()
                            .map(trackToTrackResponseConverter::convert)
                            .toList()
            );

            foundPlaylists.addAll(
                    playlistRepository
                            .findAllByTitleContainingIgnoreCase(key)
                            .stream()
                            .map(playlistToPlaylistResponseConverter::convert)
                            .toList()
            );

            foundUsers.addAll(
                    userRepository
                            .findAllByLoginContainingIgnoreCase(key)
                            .stream()
                            .map(userToUserResponseConverter::convert)
                            .toList()
            );
        }

        return SearchResponse.builder()
                .tracks(foundTracks.stream().toList())
                .playlists(foundPlaylists.stream().toList())
                .users(foundUsers.stream().toList())
                .build();
    }
}
