package server;

import java.util.*;

/*

用来记录输出流和用户的对应关系

 */
public class CrazyitMap<K, V> {
    //创建一个线程安全的HashMap
    private Map<K, V> map = Collections.synchronizedMap(new HashMap<K, V>());


    //根据value来删除制定项
    public synchronized void removeByValue(Object value) {
        for (var key : map.keySet()) {
            if (map.get(key) == value || map.get(key).equals(value)) {
                map.remove(key);
                break;
            }
        }
    }

    //根据key来删除指定项
    public synchronized void remove(Object key) {
        for (K theKey : map.keySet()) {
            if (theKey == key || theKey.equals(key)) {
                map.remove(theKey);
                break;
            }
        }
    }

    //获取所以value组成的Set集合
    public synchronized Set<V> valueSet() {
        Set<V> result = new HashSet<>();
        map.forEach((key, value) -> result.add(value));
        return result;
    }


    //根据key来找到value
    public synchronized V getValueByKey(K key) {
        for (K theKey : map.keySet()) {
            if (theKey == key || theKey.equals(key)) {
                return map.get(key);
            }
        }
        return null;
    }


    //根据value查找key
    public synchronized K getKeyByValue(V val) {
        for (var key : map.keySet()) {
            if (map.get(key) == val || map.get(key).equals(val)) {
                return key;
            }
        }
        return null;
    }

    //实现put方法，同时不允许value重复
    public synchronized V put(K key, V value) {
        for (var val : valueSet()) {
            if (val.equals(value) && val.hashCode() == value.hashCode()) {
                throw new RuntimeException("CrazyitMap实例中不允许有重复value!");
            }
        }
        return map.put(key, value);
    }
}
