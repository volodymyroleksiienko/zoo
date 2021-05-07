package com.charlie.zoo.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class PackSizeDto implements Comparable<PackSizeDto>{
    private BigDecimal count;
    private String type;

    @Override
    public int compareTo(PackSizeDto o) {
        String comp = count.doubleValue() + type;
        return comp.compareTo(o.getCount().doubleValue()+o.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackSizeDto that = (PackSizeDto) o;
        return Objects.equals(count, that.count) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, type);
    }
}
