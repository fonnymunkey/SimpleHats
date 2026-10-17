package fonnymunkey.simplehats.platform;

public interface ConditionalService {
    boolean isActive();

    default int priority() {
        return 1000;
    }
}
