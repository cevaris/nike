package com.cevaris.nike.server;

import java.util.Objects;

public class OffsetPosition {

    public final Long offset;
    public final Integer position;

    public OffsetPosition(Long offset, Integer position) {
        this.offset = offset;
        this.position = position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, position);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OffsetPosition other = (OffsetPosition) obj;
        return Objects.equals(this.offset, other.offset)
                && Objects.equals(this.position, other.position);
    }
}
