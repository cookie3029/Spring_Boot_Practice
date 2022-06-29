package com.kabigon.dto.todo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDTO {
    private Long todoNo;
    private String todoContent;
    private String  todoColor;
    private LocalDateTime expiryDate;
    private Boolean isComplete;
    
    private Boolean isSuccess;
    private String error;
}
