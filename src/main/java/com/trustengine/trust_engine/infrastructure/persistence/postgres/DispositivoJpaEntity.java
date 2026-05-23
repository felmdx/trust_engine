package com.trustengine.trust_engine.infrastructure.persistence.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_dispositivos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "device_fingerprint", nullable = false, unique = true, length = 64)
    private String deviceFingerprint;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "data_vinculacao", nullable = false)
    private LocalDateTime dataVinculacao;

    @Column(name = "is_confiavel", nullable = false)
    private boolean isConfiavel;
}