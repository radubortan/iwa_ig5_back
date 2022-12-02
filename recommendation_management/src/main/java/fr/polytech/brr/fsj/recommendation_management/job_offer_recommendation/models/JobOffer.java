package fr.polytech.brr.fsj.recommendation_management.job_offer_recommendation.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "job_offer")
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job_offer")
    private Long idJobOffer;

    private String title;

    private String description;

    @Column(name = "beginning_date")
    private Date beginningDate;

    @Column(name = "ending_date")
    private Date endingDate;

    private String place;

    @Column(name = "num_positions")
    private Integer numberPositions;

    private Double remuneration;

    @Column(name = "publishing_date")
    private Date publishingDate;

    @Column(name = "id_employer")
    private Long idEmployer;
}

