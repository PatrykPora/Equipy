package pl.elpepe.equipy.user;

import java.time.LocalDateTime;

public class UserAssignmentDto {

    private Long id;
    private LocalDateTime startRentDay;
    private LocalDateTime endRentDay;
    private Long assetId;
    private String assetName;
    private String assetSerialNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartRentDay() {
        return startRentDay;
    }

    public void setStartRentDay(LocalDateTime startRentDay) {
        this.startRentDay = startRentDay;
    }

    public LocalDateTime getEndRentDay() {
        return endRentDay;
    }

    public void setEndRentDay(LocalDateTime endRentDay) {
        this.endRentDay = endRentDay;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }
}
