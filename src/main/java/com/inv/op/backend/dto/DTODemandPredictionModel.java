package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTODemandPredictionModel {
    Long id;
    String type;
    String color;
    Integer num;
    //PMP
    String ponderations;
    //PMSE
    Double alpha;
    Integer root;
    //RL
    Integer ignorePeriods;
    //Ix
    Integer length;
    Integer count;
    Integer expectedDemand;
}
