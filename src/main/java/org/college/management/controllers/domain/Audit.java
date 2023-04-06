package org.college.management.controllers.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class Audit {
    @NotNull
    private LocalDateTime insertTime ;
    @NotNull
    private LocalDateTime lastUpdatedTime ;
    @NotNull
    private String operator;
}
