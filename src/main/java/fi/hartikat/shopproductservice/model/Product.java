package fi.hartikat.shopproductservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID uuid;

    @Column
    @NotNull
    @Schema(type = "string", allowableValues = {"EUR"})
    private Currency currency;

    @Column
    @NotNull
    private BigDecimal cost;

    @Column
    @NotEmpty
    private String name;

    @Column
    private String description;
}
