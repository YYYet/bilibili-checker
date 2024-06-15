package com.bili.common.entity.bili;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class PayloadData {
        private List<String> taskIds;
        private String cmd;
    }
