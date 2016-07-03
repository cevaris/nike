package com.cevaris.nike.client;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HashCodePartitionStrategyTest {

    @Test
    public void testHashCodeCycle() {
        int partitionSize = 8;
        PartitionStrategy<TestObject> strategy = new HashCodePartitionStrategy<TestObject>();

        List<Integer> expected = Lists.newArrayList(6, 4, 2, 3, 1, 5, 0, 7, 1, 1, 6, 0, 3, 4, 3, 0);
        List<Integer> actual = Lists.newArrayList();
        List<TestObject> testObjects = listOfTestObjects(partitionSize * 2);

        for (TestObject obj : testObjects) {
            actual.add(strategy.getPartition(partitionSize, obj));
        }

        assertEquals(expected, actual);
    }


    private class TestObject {
        private String value;

        public TestObject(String ival) {
            this.value = ival;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final TestObject other = (TestObject) obj;
            return Objects.equal(this.value, other.value);
        }
    }

    private List<TestObject> listOfTestObjects(Integer n) {
        List<TestObject> data = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            data.add(new TestObject(DigestUtils.md5Hex(String.valueOf(i))));
        }
        return data;
    }
}