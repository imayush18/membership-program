package membership_program.enums;

public enum TierLevel {
    SILVER(1),
    GOLD(2),
    PLATINUM(3);

    private final int level;

    TierLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public TierLevel next() {
        TierLevel[] values = values();
        int idx = this.ordinal() + 1;
        if (idx >= values.length) throw new IllegalStateException("Already at highest tier");
        return values[idx];
    }

    public TierLevel previous() {
        int idx = this.ordinal() - 1;
        if (idx < 0) throw new IllegalStateException("Already at lowest tier");
        return TierLevel.values()[idx];
    }
}
