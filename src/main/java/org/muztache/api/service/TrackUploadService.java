package org.muztache.api.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.track.request.TrackUpdateRequest;
import org.muztache.api.dto.track.request.TrackUploadRequest;
import org.muztache.api.exceptions.common.CoverUploadingException;
import org.muztache.api.exceptions.track.TrackUploadingException;
import org.muztache.api.exceptions.user.UserNotFoundException;
import org.muztache.api.model.Clip;
import org.muztache.api.model.Track;
import org.muztache.api.model.User;
import org.muztache.api.repository.TrackRepository;
import org.muztache.api.repository.UserRepository;
import org.muztache.api.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class TrackUploadService {

    private final TrackRepository trackRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final CloudinaryService cloudinaryService;

    public void upload(@Valid TrackUploadRequest trackUploadRequest) {
        try {
            File audioFile = MultipartFileUtils.asFile(trackUploadRequest.getAudioFile());
            File cover = MultipartFileUtils.asFile(trackUploadRequest.getCover());

            File smallCover;

            if (trackUploadRequest.getSmallCover() == null) {
                smallCover = cover;
            } else {
                smallCover = MultipartFileUtils.asFile(trackUploadRequest.getSmallCover());
            }

            MultipartFile clipMultipart = trackUploadRequest.getClipFile();

            Clip clip = null;

            if (clipMultipart != null) {
                File clipFile = MultipartFileUtils.asFile(clipMultipart);
                String clipFileUri = cloudinaryService.upload(clipFile, CloudinaryService.UploadType.VIDEO);
                clip = Clip.builder()
                        .fileUri(clipFileUri)
                        .startMs(trackUploadRequest.getClipStart())
                        .endMs(trackUploadRequest.getClipEnd())
                        .build();
            }

            String audioUri = cloudinaryService.upload(audioFile, CloudinaryService.UploadType.AUDIO);
            String smallCoverUri = cloudinaryService.upload(smallCover, CloudinaryService.UploadType.SMALL_COVER);
            String coverUri = cloudinaryService.upload(cover, CloudinaryService.UploadType.COVER);

            Track track = Track.builder()
                    .title(trackUploadRequest.getTitle())
                    .audioFileUri(audioUri)
                    .smallCoverUri(smallCoverUri)
                    .genre(trackUploadRequest.getGenre())
                    .coverUri(coverUri)
                    .lyrics(trackUploadRequest.getLyrics())
                    .releaseDate(Date.valueOf(LocalDate.now()))
                    .clip(clip)
                    .build();

            User authorizedUser = userService.getCurrentUser();
            authorizedUser.getTracks().add(track);

            for (Long id : trackUploadRequest.getAuthorIds()) {
                userRepository.findById(id).ifPresentOrElse(
                        user -> user.getTracks().add(track),
                        () -> { throw new UserNotFoundException(id); }
                );
            }

            trackRepository.saveAndFlush(track);

        } catch (IOException exception) {
            throw new TrackUploadingException(exception);
        }
    }

    public void update(TrackUpdateRequest trackUpdateRequest) {

        Optional<Track> optionalTrack = trackRepository
                .findMusicById(trackUpdateRequest.getId());

        if (optionalTrack.isPresent()) {
            Track track = optionalTrack.get();

            String title = trackUpdateRequest.getTitle();
            String lyrics = trackUpdateRequest.getLyrics();

            MultipartFile newCover = trackUpdateRequest.getCover();
            MultipartFile newSmallCover = trackUpdateRequest.getSmallCover();

            if (title != null) {
                track.setTitle(title);
            }
            if (lyrics != null) {
                track.setLyrics(lyrics);
            }
            if (newCover != null) {
                try {
                    File file = MultipartFileUtils.asFile(trackUpdateRequest.getCover());
                    String newCoverUri = cloudinaryService
                            .upload(file, CloudinaryService.UploadType.COVER);
                    track.setCoverUri(newCoverUri);
                } catch (IOException exception) {
                    throw new CoverUploadingException(exception);
                }
            }
            if (newSmallCover != null) {
                try {
                    File file = MultipartFileUtils.asFile(trackUpdateRequest.getSmallCover());
                    String newSmallCoverUri = cloudinaryService
                            .upload(file, CloudinaryService.UploadType.SMALL_COVER);
                    track.setSmallCoverUri(newSmallCoverUri);
                } catch (IOException exception) {
                    throw new CoverUploadingException(exception);
                }
            }

            trackRepository.save(track);
        }
    }

    public void delete(Long trackId) {
        trackRepository.deleteById(trackId);
    }
}
