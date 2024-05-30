package org.muztache.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "playlists")
@SecondaryTables(@SecondaryTable(name = "users_playlists"))
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_time", nullable = false)
    private Timestamp createTime;

    @Column(name = "cover", nullable = false)
    private String coverUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(
            table = "users_playlists",
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @ManyToMany(mappedBy = "playlists", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Track> tracks = new HashSet<>();
}
