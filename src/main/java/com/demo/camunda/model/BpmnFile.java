package com.demo.camunda.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "bpmn_file")
public class BpmnFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "file_blob", columnDefinition = "LONGBLOB")
    private byte[] fileBlob;


    @Column(name = "description")
    private String description;
}
