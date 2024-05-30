package org.muztache.api.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.muztache.api.controller.rest.playlist.PlaylistUpdateRequest;
import org.muztache.api.dto.playlist.request.PlaylistUploadRequest;
import org.muztache.api.exceptions.common.CoverUploadingException;
import org.muztache.api.exceptions.playlist.PlaylistUploadingException;
import org.muztache.api.model.Playlist;
import org.muztache.api.model.User;
import org.muztache.api.repository.PlaylistRepository;
import org.muztache.api.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlaylistUploadService {

    private final UserService userService;

    private final PlaylistRepository playlistRepository;

    private final CloudinaryService cloudinaryService;

    public void insert(@Valid PlaylistUploadRequest playlistUploadRequest) {
        try {
            String coverUri = cloudinaryService.upload(
                    MultipartFileUtils.asFile(playlistUploadRequest.getCover()),
                    CloudinaryService.UploadType.COVER
            );

            Playlist playlist = Playlist.builder()
                    .title(playlistUploadRequest.getTitle())
                    .coverUrl(coverUri)
                    .createTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();

            User authorizedUser = userService.getCurrentUser();
            authorizedUser.getPlaylists().add(playlist);

            playlistRepository.save(playlist);
        } catch (IOException exception) {
            throw new PlaylistUploadingException(exception);
        }
    }

    public void update(PlaylistUpdateRequest playlistUpdateRequest) {

        Optional<Playlist> optionalPlaylist = playlistRepository
                .findPlaylistById(playlistUpdateRequest.getId());

        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();

            MultipartFile multipartFile = playlistUpdateRequest.getAvatar();
            String title = playlistUpdateRequest.getTitle();

            if (multipartFile != null) {
                try {
                    File file = MultipartFileUtils.asFile(multipartFile);
                    String newCoverUri = cloudinaryService.upload(file, CloudinaryService.UploadType.COVER);
                    playlist.setCoverUrl(newCoverUri);
                } catch (IOException exception) {
                    throw new CoverUploadingException(exception);
                }
            }
            if (title != null) {
                playlist.setTitle(title);
            }

            playlistRepository.save(playlist);
        }
    }

    public void delete(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }
}
