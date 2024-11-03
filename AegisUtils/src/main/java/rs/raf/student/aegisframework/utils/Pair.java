package rs.raf.student.aegisframework.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
public class Pair<Key, Value> implements Map.Entry<Key, Value> {

    private final Key   key;
    private       Value value;

    public static<Key, Value> Pair<Key, Value> of(Key key, Value value) {
        return new Pair<>(key, value);
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public Value setValue(Value value) {
        return this.value = value;
    }

}
