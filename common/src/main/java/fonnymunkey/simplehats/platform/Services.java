package fonnymunkey.simplehats.platform;

import java.util.Comparator;
import java.util.ServiceLoader;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.platform.services.IAccessoryHelper;
import fonnymunkey.simplehats.platform.services.IPlatformHelper;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IAccessoryHelper ACCESSORY = load(IAccessoryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
            .stream()
            .map(ServiceLoader.Provider::get)
            .filter(e -> {
                if (e instanceof ConditionalService conditionalService) {
                    return conditionalService.isActive();
                }

                return true;
            })
            .sorted(Comparator.comparingInt(value -> {
                if (value instanceof ConditionalService conditionalService) {
                    return conditionalService.priority();
                }

                return 1000;
            }))
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}