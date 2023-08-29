package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;

@Entity
public class ImportDetails {

    @Id
    @GeneratedValue
    @Column(name = "ma_so")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ma_nhap_hang", nullable = false)
    private Import anImport;




}
