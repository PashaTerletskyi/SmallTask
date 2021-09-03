import java.util.HashMap;
import java.util.Map;

public class LimitedSetImpl<T> implements LimitedSet<T> {
    private static final int MAX_SIZE = 10;
    private final Map<T, Integer> map;

    public LimitedSetImpl() {
       map = new HashMap<>();
    }

    @Override
    public void add(T t) {
        map.put(t, 0);
        if (map.size() > MAX_SIZE) {
            resize();
        }
    }

    @Override
    public boolean remove(T t) {
        return map.remove(t, map.get(t));
    }

    @Override
    public boolean contains(T t) {
        if (map.containsKey(t)) {
            map.put(t, map.get(t) + 1);
            return true;
        }
        return false;
    }

    private void resize() {
        Integer minValue = map.values().stream()
                .min(Integer::compareTo)
                .get();
        T t = map.entrySet()
                .stream()
                .filter(entry -> minValue.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny()
                .get();
        remove(t);
    }
}
