package com.parkinglot.entities;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Car {

    private String licensePlate = "";

    public Car() {
        licensePlate = IdUtil.getSnowflake().nextIdStr();
    }

}
