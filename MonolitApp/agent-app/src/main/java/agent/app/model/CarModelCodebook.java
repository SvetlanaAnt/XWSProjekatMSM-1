package agent.app.model;

import agent.app.common.db.DbColumnConstants;
import agent.app.common.db.DbTableConstants;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = DbTableConstants.CARMODELCODEBOOK)
public class CarModelCodebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DbColumnConstants.NAME, unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarManufacturerCodebook carManufacturerCodebook;
}