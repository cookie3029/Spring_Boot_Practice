package com.kabigon.dto.group;

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
public class GroupMemberDTO {
    private Long groupNo;
    private Long userNo;
    private Boolean applyState;
}
