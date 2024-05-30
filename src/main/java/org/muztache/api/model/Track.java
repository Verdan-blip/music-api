package org.muztache.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "cover_uri", nullable = false)
    private String coverUri;

    @Column(name = "small_cover_uri", nullable = false)
    private String smallCoverUri;

    @Column(name = "audio_file_uri", nullable = false)
    private String audioFileUri;

    @Column(name = "lyrics")
    private String lyrics;

    @ManyToMany(mappedBy = "tracks", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "track", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TrackPlay> trackPlays = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tracks_playlists",
            joinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id")
    )
    private Set<Playlist> playlists = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "clip_id", referencedColumnName = "id")
    private Clip clip;
}
