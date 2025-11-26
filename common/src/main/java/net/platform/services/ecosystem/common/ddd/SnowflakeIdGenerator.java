package net.platform.services.ecosystem.common.ddd;

public class SnowflakeIdGenerator {
    private final long epoch = 1609459200000L;
    private final long datacenterIdBits = 5L;
    private final long machineIdBits = 5L;
    private final long sequenceBits = 12L;
    private final long maxDatacenterId = 31L;
    private final long maxMachineId = 31L;
    private final long machineIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static SnowflakeIdGenerator INSTANCE;

    private SnowflakeIdGenerator() {
    }

    public static SnowflakeIdGenerator instance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("SnowflakeIdGenerator not initialized");
        } else {
            return INSTANCE;
        }
    }

    public static synchronized SnowflakeIdGenerator createInstance() {
        if (INSTANCE == null) {
            long datacenterId = 0;
            long machineId = 0;
            INSTANCE = new SnowflakeIdGenerator(datacenterId, machineId);
        }

        return INSTANCE;
    }

    public static SnowflakeIdGenerator createInstance(long datacenterId, long machineId) {
        INSTANCE = new SnowflakeIdGenerator(datacenterId, machineId);
        return INSTANCE;
    }

    private SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId <= 31L && datacenterId >= 0L) {
            if (machineId <= 31L && machineId >= 0L) {
                this.datacenterId = datacenterId;
                this.machineId = machineId;
            } else {
                throw new IllegalArgumentException("Machine ID out of range");
            }
        } else {
            throw new IllegalArgumentException("Datacenter ID out of range");
        }
    }

    private synchronized long nextId() {
        long timestamp = this.currentTime();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        } else {
            if (timestamp == this.lastTimestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.waitNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1609459200000L << 22 | this.datacenterId << 17 | this.machineId << 12 | this.sequence;
        }
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp;
        for (timestamp = this.currentTime(); timestamp <= lastTimestamp; timestamp = this.currentTime()) {
        }

        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    public Long createValue() {
        return this.nextId();
    }
}
