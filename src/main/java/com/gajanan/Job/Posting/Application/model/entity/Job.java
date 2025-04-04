package com.gajanan.Job.Posting.Application.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "jobs")
@Getter
@Setter
@ToString
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String location;

    private Double salary;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date createdAt;


    public Job(String title, String company, String description, String location, Double salary) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.location = location;
        this.salary = salary;
    }


}
