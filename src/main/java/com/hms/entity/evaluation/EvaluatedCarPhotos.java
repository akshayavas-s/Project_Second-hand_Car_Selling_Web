package com.hms.entity.evaluation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evaluated_car_photos")
public class EvaluatedCarPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "photo_url", length = 350)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "detailed_car_evaluation_id")
    private DetailedCarEvaluation detailedCarEvaluation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public DetailedCarEvaluation getDetailedCarEvaluation() {
        return detailedCarEvaluation;
    }

    public void setDetailedCarEvaluation(DetailedCarEvaluation detailedCarEvaluation) {
        this.detailedCarEvaluation = detailedCarEvaluation;
    }
}