import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public enum EventType {
    EVENT_TYPE_A,
    EVENT_TYPE_B,
    EVENT_TYPE_C;
}

public interface Observer {
    void onEvent(EventType eventType);
}

public class ObserverManager {
    private Set<Observer> m_weakReferencedObservers;

    public ObserverManager() {
        m_weakReferencedObservers = Collections.newSetFromMap(new WeakHashMap<>());
    }

    public void add(Observer observer){
        if (observer != null) {
            m_weakReferencedObservers.add(observer);
        }
    }

    /**
     * @param observer
     */
    public void remove(Observer observer){
        m_weakReferencedObservers.remove(observer);
    }

    public void callEvent(EventType eventType){
        m_weakReferencedObservers.stream()
                .filter(Objects::nonNull)
                .forEach(observer -> observer.onEvent(eventType));

        m_weakReferencedObservers.removeIf(Objects::isNull);
    }
}

public class EventManager {
    private Map<EventType, ObserverManager> m_observerManagers = new ConcurrentHashMap<>();

    public void registerObserver(EventType eventType, Observer observer){
        synchronized (m_observerManagers){
            m_observerManagers.computeIfAbsent(eventType, key -> new ObserverManager()).add(observer);
        }
    }

    public void unregisterObserver(EventType eventType, Observer observer){
        synchronized (m_observerManagers) {
            if (m_observerManagers.containsKey(eventType)) {
                ObserverManager observerManager = m_observerManagers.get(eventType);
                observerManager.remove(observer);
            }
        }
    }

    public void callEvent(EventType eventType){
        ObserverManager observerManager = m_observerManagers.get(eventType);
        if (observerManager != null) {
            observerManager.callEvent(eventType);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        // Create an event manager
        EventManager eventManager = new EventManager();

        // Create some observers
        Observer observer1 = eventType -> System.out.println("Observer 1 received event: " + eventType);
        Observer observer2 = eventType -> System.out.println("Observer 2 received event: " + eventType);
        Observer observer3 = eventType -> System.out.println("Observer 3 received event: " + eventType);

        // Register observers for different event types
        eventManager.registerObserver(EventType.EVENT_TYPE_A, observer1);
        eventManager.registerObserver(EventType.EVENT_TYPE_B, observer2);
        eventManager.registerObserver(EventType.EVENT_TYPE_A, observer3);

        // Call events
        eventManager.callEvent(EventType.EVENT_TYPE_A);
        eventManager.callEvent(EventType.EVENT_TYPE_B);

        // Unregister observer
        eventManager.unregisterObserver(EventType.EVENT_TYPE_A, observer1);

        // Call event after unregistering
        eventManager.callEvent(EventType.EVENT_TYPE_A);
    }
}
