package pl.elpepe.equipy.assigment;


import jakarta.persistence.*;
import pl.elpepe.equipy.asset.Asset;
import pl.elpepe.equipy.user.User;

import java.time.LocalDateTime;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startRentTime;
    private LocalDateTime endRentTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartRentTime() {
        return startRentTime;
    }

    public void setStartRentTime(LocalDateTime startRentTime) {
        this.startRentTime = startRentTime;
    }

    public LocalDateTime getEndRentTime() {
        return endRentTime;
    }

    public void setEndRentTime(LocalDateTime endRentTime) {
        this.endRentTime = endRentTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
