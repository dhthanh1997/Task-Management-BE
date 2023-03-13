package com.ansv.taskmanagement.dto.response;



import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO extends BaseDTO<String>   {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Length(max = 500)
    private String name;

    private Long customerId;

    private BigDecimal totalCost;

    private BigDecimal revenue;

    private Float totalHour;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime readStartDate;

    private LocalDateTime readEndDate;

    private Long parentId;

    private String attachFile;

    private Byte state;

}
